import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { customerService } from "@/services/admin/customerService";
import { toast } from "sonner";
import { Button } from "@/components/ui/button";

type CareItem = {
  id: number;
  staffName: string;
  content: string;
  statusAfterCare: string;
  careDate: string;
};

export default function CustomerCareHistory() {
  const { id } = useParams();
  const [items, setItems] = useState<CareItem[]>([]);
  const [content, setContent] = useState("");
  const [statusAfterCare, setStatusAfterCare] = useState("DANG_CHAM_SOC");
  const [loading, setLoading] = useState(false);

  const fetchHistory = async () => {
    try {
      const res = await customerService.getCareHistory(id!);
      setItems(res.data || []);
    } catch (error) {
      console.error(error);
      toast.error("Tải lịch sử chăm sóc thất bại");
    }
  };

  useEffect(() => {
    fetchHistory();
  }, [id]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      setLoading(true);

      await customerService.createCareHistory({
        customerId: Number(id),
        content,
        statusAfterCare,
      });

      toast.success("Thêm lịch sử chăm sóc thành công");
      setContent("");
      setStatusAfterCare("DANG_CHAM_SOC");
      fetchHistory();
    } catch (error) {
      console.error(error);
      toast.error("Thêm lịch sử chăm sóc thất bại");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="space-y-5">
      <h1 className="text-xl font-semibold">Lịch sử chăm sóc khách hàng</h1>

      <form onSubmit={handleSubmit} className="p-4 space-y-4 bg-white border rounded-xl">
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="Nhập nội dung chăm sóc khách hàng"
          className="w-full p-3 border rounded-xl"
        />

        <select
          value={statusAfterCare}
          onChange={(e) => setStatusAfterCare(e.target.value)}
          className="w-full p-3 border rounded-xl"
        >
          <option value="MOI">Mới</option>
          <option value="DANG_CHAM_SOC">Đang chăm sóc</option>
          <option value="DA_CHOT">Đã chốt</option>
          <option value="NGUNG_THEO_DOI">Ngừng theo dõi</option>
        </select>

        <Button type="submit" disabled={loading}>
          {loading ? "Đang lưu..." : "Thêm lịch sử"}
        </Button>
      </form>

      <div className="overflow-hidden bg-white border rounded-xl">
        <table className="w-full">
          <thead>
            <tr className="border-b bg-slate-50">
              <th className="p-3 text-left">Staff</th>
              <th className="p-3 text-left">Nội dung</th>
              <th className="p-3 text-left">Trạng thái sau CSKH</th>
              <th className="p-3 text-left">Ngày chăm sóc</th>
            </tr>
          </thead>

          <tbody>
            {items.length === 0 ? (
              <tr>
                <td colSpan={4} className="p-4 text-center">
                  Chưa có lịch sử chăm sóc
                </td>
              </tr>
            ) : (
              items.map((item) => (
                <tr key={item.id} className="border-b">
                  <td className="p-3">{item.staffName}</td>
                  <td className="p-3">{item.content}</td>
                  <td className="p-3">{item.statusAfterCare}</td>
                  <td className="p-3">
                    {new Date(item.careDate).toLocaleString("vi-VN")}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}