import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "sonner";
import TransactionForm from "./TransactionForm";
import { transactionService } from "@/services/admin/transactionService";
import type { TransactionFormData } from "@/types/admin/transactions";
import api from "@/lib/axios";

type OptionItem = {
  id: number;
  label: string;
};

export default function EditTransaction() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [customers, setCustomers] = useState<OptionItem[]>([]);
  const [staffs, setStaffs] = useState<OptionItem[]>([]);
  const [initialValues, setInitialValues] = useState<TransactionFormData>();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const detailRes = await transactionService.getDetail(id!);

        const customerRes = await api.get("/customers", {
          params: { page: 1, limit: 1000 },
        });
        const staffRes = await api.get("/users", {
          params: { page: 1, limit: 1000 },
        });

        setCustomers(
          (customerRes.data.data.customers || []).map((item: any) => ({
            id: item.id,
            label: item.fullName,
          })),
        );

        setStaffs(
          (staffRes.data.data.users || []).map((item: any) => ({
            id: item.id,
            label: item.fullName,
          })),
        );

        const item = detailRes.data;

        setInitialValues({
          customerId: String(item.customerId ?? ""),
          staffId: String(item.staffId ?? ""),
          transactionType: item.transactionType ?? "TU_VAN",
          transactionStatus: item.transactionStatus ?? "MOI",
          content: item.content ?? "",
          transactionDate: item.transactionDate ?? "",
        });
      } catch (error) {
        console.error(error);
        toast.error("Tải dữ liệu giao dịch thất bại");
      }
    };

    fetchData();
  }, [id]);

  const handleSubmit = async (values: TransactionFormData) => {
    try {
      await transactionService.update(id!, values);
      toast.success("Cập nhật giao dịch thành công");
      navigate("/admin/transactions");
    } catch (error) {
      console.error(error);
      toast.error("Cập nhật giao dịch thất bại");
    }
  };

  if (!initialValues) return <div>Đang tải...</div>;

  return (
    <div className="space-y-4">
      <h1 className="text-xl font-semibold">Chỉnh sửa giao dịch</h1>
      <TransactionForm
        initialValues={initialValues}
        customers={customers}
        staffs={staffs}
        onSubmit={handleSubmit}
        submitText="Cập nhật"
      />
    </div>
  );
}