import { useEffect, useState } from "react";
import { customerService } from "@/services/admin/customerService";
import { toast } from "sonner";
import { Button } from "@/components/ui/button";

type StaffItem = {
  staffId: number;
  fullName: string;
  checked: boolean;
};

type Props = {
  open: boolean;
  onClose: () => void;
  customerId: number | null;
  onSuccess?: () => void;
};

export default function AssignCustomerDialog({
  open,
  onClose,
  customerId,
  onSuccess,
}: Props) {
  const [staffs, setStaffs] = useState<StaffItem[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!open || !customerId) return;

    const fetchStaffs = async () => {
      try {
        const res = await customerService.getStaffs(customerId);
        setStaffs(res.data || []);
      } catch (error) {
        console.error(error);
        toast.error("Tải danh sách staff thất bại");
      }
    };

    fetchStaffs();
  }, [open, customerId]);

  const handleToggle = (staffId: number) => {
    setStaffs((prev) =>
      prev.map((item) =>
        item.staffId === staffId ? { ...item, checked: !item.checked } : item,
      ),
    );
  };

  const handleSubmit = async () => {
    if (!customerId) return;

    try {
      setLoading(true);

      const selectedStaffs = staffs
        .filter((item) => item.checked)
        .map((item) => item.staffId);

      await customerService.assign(customerId, selectedStaffs);

      toast.success("Giao khách hàng thành công");
      onClose();
      onSuccess?.();
    } catch (error) {
      console.error(error);
      toast.error("Giao khách hàng thất bại");
    } finally {
      setLoading(false);
    }
  };

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/30">
      <div className="w-full max-w-lg p-6 bg-white shadow-lg rounded-2xl">
        <h2 className="mb-4 text-lg font-semibold">Giao khách hàng cho staff</h2>

        <div className="space-y-3 max-h-[300px] overflow-y-auto">
          {staffs.length === 0 ? (
            <p className="text-sm text-slate-500">Không có staff</p>
          ) : (
            staffs.map((item) => (
              <label
                key={item.staffId}
                className="flex items-center gap-3 p-3 border rounded-xl"
              >
                <input
                  type="checkbox"
                  checked={item.checked}
                  onChange={() => handleToggle(item.staffId)}
                />
                <span>{item.fullName}</span>
              </label>
            ))
          )}
        </div>

        <div className="flex justify-end gap-2 mt-5">
          <Button variant="outline" onClick={onClose}>
            Đóng
          </Button>
          <Button onClick={handleSubmit} disabled={loading}>
            {loading ? "Đang lưu..." : "Lưu"}
          </Button>
        </div>
      </div>
    </div>
  );
}