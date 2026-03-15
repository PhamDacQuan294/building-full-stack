import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "sonner";
import CustomerForm from "./CustomerForm";
import { customerService } from "@/services/admin/customerService";
import type { CustomerFormData } from "@/types/admin/customers";

export default function EditCustomer() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [initialValues, setInitialValues] = useState<CustomerFormData>();

  useEffect(() => {
    const fetchDetail = async () => {
      try {
        const res = await customerService.getDetail(id!);
        setInitialValues(res.data);
      } catch (error) {
        console.error(error);
        toast.error("Tải chi tiết khách hàng thất bại");
      }
    };

    fetchDetail();
  }, [id]);

  const handleSubmit = async (values: CustomerFormData) => {
    try {
      await customerService.update(id!, values);
      toast.success("Cập nhật khách hàng thành công");
      navigate("/admin/customers");
    } catch (error) {
      console.error(error);
      toast.error("Cập nhật khách hàng thất bại");
    }
  };

  if (!initialValues) return <div>Đang tải...</div>;

  return (
    <div className="space-y-4">
      <h1 className="text-xl font-semibold">Chỉnh sửa khách hàng</h1>
      <CustomerForm
        initialValues={initialValues}
        onSubmit={handleSubmit}
        submitText="Cập nhật"
      />
    </div>
  );
}