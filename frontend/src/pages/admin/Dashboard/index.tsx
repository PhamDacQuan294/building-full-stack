import { useEffect, useState } from "react";
import { dashboardService } from "@/services/admin/dashboardService";
import type { DashboardData } from "@/types/admin/dashboard";
import { toast } from "sonner";
import {
  BarChart3,
  Building2,
  Users,
  ShieldCheck,
  Handshake,
  UserPlus,
} from "lucide-react";

import {
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  PieChart,
  Pie,
  Cell,
  Legend,
} from "recharts";

const COLORS = ["#7c3aed", "#06b6d4", "#22c55e", "#f59e0b", "#ef4444", "#3b82f6"];

type StatCardProps = {
  title: string;
  value: number;
  icon: React.ReactNode;
};

function StatCard({ title, value, icon }: StatCardProps) {
  return (
    <div className="p-5 bg-white border shadow-sm rounded-2xl">
      <div className="flex items-center justify-between mb-3">
        <div className="text-sm text-slate-500">{title}</div>
        <div className="text-violet-600">{icon}</div>
      </div>
      <div className="text-3xl font-bold text-slate-800">{value}</div>
    </div>
  );
}

function mapTransactionStatusLabel(value: string) {
  switch (value) {
    case "MOI":
      return "Mới";
    case "DANG_XU_LY":
      return "Đang xử lý";
    case "HOAN_THANH":
      return "Hoàn thành";
    case "HUY":
      return "Hủy";
    default:
      return value;
  }
}

function mapCustomerStatusLabel(value: string) {
  switch (value) {
    case "MOI":
      return "Mới";
    case "DANG_CHAM_SOC":
      return "Đang chăm sóc";
    case "DA_CHOT":
      return "Đã chốt";
    case "NGUNG_THEO_DOI":
      return "Ngừng theo dõi";
    default:
      return value;
  }
}

export default function DashboardPage() {
  const [data, setData] = useState<DashboardData | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchDashboard = async () => {
      try {
        setLoading(true);
        const res = await dashboardService.getDashboard();
        setData(res.data);
      } catch (error) {
        console.error(error);
        toast.error("Tải dữ liệu dashboard thất bại");
      } finally {
        setLoading(false);
      }
    };

    fetchDashboard();
  }, []);

  if (loading) {
    return <div className="p-6">Đang tải dashboard...</div>;
  }

  if (!data) {
    return <div className="p-6">Không có dữ liệu dashboard</div>;
  }

  const transactionStatusData = data.transactionStatusStats.map((item) => ({
    ...item,
    name: mapTransactionStatusLabel(item.name),
  }));

  const customerStatusData = data.customerStatusStats.map((item) => ({
    ...item,
    name: mapCustomerStatusLabel(item.name),
  }));

  return (
    <div className="space-y-6">
      <div className="flex items-center gap-2">
        <BarChart3 className="w-6 h-6 text-violet-600" />
        <h1 className="text-2xl font-bold">Tổng quan hệ thống</h1>
      </div>

      <div className="grid grid-cols-1 gap-4 md:grid-cols-2 xl:grid-cols-5">
        <StatCard title="Tổng user" value={data.totalUsers} icon={<Users className="w-5 h-5" />} />
        <StatCard title="Tổng role" value={data.totalRoles} icon={<ShieldCheck className="w-5 h-5" />} />
        <StatCard title="Tổng tòa nhà" value={data.totalBuildings} icon={<Building2 className="w-5 h-5" />} />
        <StatCard title="Tổng khách hàng" value={data.totalCustomers} icon={<Users className="w-5 h-5" />} />
        <StatCard title="Tổng giao dịch" value={data.totalTransactions} icon={<Handshake className="w-5 h-5" />} />
      </div>

      <div className="grid grid-cols-1 gap-4 md:grid-cols-3">
        <StatCard title="Building active" value={data.activeBuildings} icon={<Building2 className="w-5 h-5" />} />
        <StatCard title="Building inactive" value={data.inactiveBuildings} icon={<Building2 className="w-5 h-5" />} />
        <StatCard title="Khách hàng mới trong tháng" value={data.newCustomersThisMonth} icon={<UserPlus className="w-5 h-5" />} />
      </div>

      <div className="grid grid-cols-1 gap-6 xl:grid-cols-2">
        <div className="p-5 bg-white border shadow-sm rounded-2xl">
          <h2 className="mb-4 text-lg font-semibold">Giao dịch theo trạng thái</h2>
          <div className="h-[320px]">
            <ResponsiveContainer width="100%" height="100%">
              <PieChart>
                <Pie
                  data={transactionStatusData}
                  dataKey="value"
                  nameKey="name"
                  outerRadius={110}
                  label
                >
                  {transactionStatusData.map((_, index) => (
                    <Cell key={index} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip />
                <Legend />
              </PieChart>
            </ResponsiveContainer>
          </div>
        </div>

        <div className="p-5 bg-white border shadow-sm rounded-2xl">
          <h2 className="mb-4 text-lg font-semibold">Khách hàng theo trạng thái</h2>
          <div className="h-[320px]">
            <ResponsiveContainer width="100%" height="100%">
              <PieChart>
                <Pie
                  data={customerStatusData}
                  dataKey="value"
                  nameKey="name"
                  outerRadius={110}
                  label
                >
                  {customerStatusData.map((_, index) => (
                    <Cell key={index} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip />
                <Legend />
              </PieChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>

      <div className="p-5 bg-white border shadow-sm rounded-2xl">
        <h2 className="mb-4 text-lg font-semibold">Thống kê giao dịch theo tháng</h2>
        <div className="h-[350px]">
          <ResponsiveContainer width="100%" height="100%">
            <BarChart data={data.monthlyTransactionStats}>
              <XAxis dataKey="name" />
              <YAxis />
              <Tooltip />
              <Bar dataKey="value" radius={[10, 10, 0, 0]} fill="#7c3aed" />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
}