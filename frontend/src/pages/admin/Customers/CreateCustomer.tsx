import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import CustomerForm from "./CustomerForm";
import { customerService } from "@/services/admin/customerService";
import type { CustomerFormData } from "@/types/admin/customers";

export default function CreateCustomer() {
  const navigate = useNavigate();

  const handleSubmit = async (values: CustomerFormData) => {
    try {
      await customerService.create(values);
      toast.success("Thêm khách hàng thành công");
      navigate("/admin/customers");
    } catch (error) {
      console.error(error);
      toast.error("Thêm khách hàng thất bại");
    }
  };

  return (
    <div className="space-y-4">
      <h1 className="text-xl font-semibold">Thêm khách hàng</h1>
      <CustomerForm onSubmit={handleSubmit} submitText="Thêm mới" />
    </div>
  );
}