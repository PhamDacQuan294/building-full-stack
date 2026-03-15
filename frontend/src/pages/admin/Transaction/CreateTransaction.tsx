import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import TransactionForm from "./TransactionForm";
import { transactionService } from "@/services/admin/transactionService";
import type { TransactionFormData } from "@/types/admin/transactions";
import api from "@/lib/axios";

type OptionItem = {
  id: number;
  label: string;
};

export default function CreateTransaction() {
  const navigate = useNavigate();
  const [customers, setCustomers] = useState<OptionItem[]>([]);
  const [staffs, setStaffs] = useState<OptionItem[]>([]);

  useEffect(() => {
    const fetchOptions = async () => {
      try {
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
      } catch (error) {
        console.error(error);
      }
    };

    fetchOptions();
  }, []);

  const handleSubmit = async (values: TransactionFormData) => {
    try {
      await transactionService.create(values);
      toast.success("Thêm giao dịch thành công");
      navigate("/admin/transactions");
    } catch (error) {
      console.error(error);
      toast.error("Thêm giao dịch thất bại");
    }
  };

  return (
    <div className="space-y-4">
      <h1 className="text-xl font-semibold">Thêm giao dịch</h1>
      <TransactionForm
        customers={customers}
        staffs={staffs}
        onSubmit={handleSubmit}
        submitText="Thêm mới"
      />
    </div>
  );
}