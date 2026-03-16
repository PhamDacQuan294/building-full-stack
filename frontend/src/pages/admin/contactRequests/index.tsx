import { useEffect } from "react";
import { useAdminContactRequestStore } from "@/stores/admin/useContactRequestStore";

export default function AdminContactRequestPage() {
  const { items, loading, fetchAll, updateStatus } = useAdminContactRequestStore();

  useEffect(() => {
    fetchAll();
  }, [fetchAll]);

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold">Yêu cầu liên hệ / tư vấn</h1>
        <p className="text-sm text-slate-500">
          Danh sách khách hàng gửi yêu cầu từ phía client
        </p>
      </div>

      {loading ? (
        <div>Đang tải dữ liệu...</div>
      ) : (
        <div className="overflow-hidden bg-white border rounded-2xl">
          <table className="min-w-full text-sm">
            <thead className="bg-slate-50">
              <tr>
                <th className="px-4 py-3 text-left">Khách hàng</th>
                <th className="px-4 py-3 text-left">Điện thoại</th>
                <th className="px-4 py-3 text-left">Email</th>
                <th className="px-4 py-3 text-left">Bất động sản</th>
                <th className="px-4 py-3 text-left">Ngày tạo</th>
                <th className="px-4 py-3 text-left">Trạng thái</th>
              </tr>
            </thead>
            <tbody>
              {items.map((item) => (
                <tr key={item.id} className="border-t">
                  <td className="px-4 py-3">{item.fullName}</td>
                  <td className="px-4 py-3">{item.phone}</td>
                  <td className="px-4 py-3">{item.email}</td>
                  <td className="px-4 py-3">{item.buildingName || "-"}</td>
                  <td className="px-4 py-3">{item.createdDate || "-"}</td>
                  <td className="px-4 py-3">
                    <select
                      value={item.status}
                      onChange={(e) => updateStatus(item.id, e.target.value)}
                      className="px-3 py-2 border rounded-lg"
                    >
                      <option value="NEW">NEW</option>
                      <option value="PROCESSING">PROCESSING</option>
                      <option value="DONE">DONE</option>
                      <option value="CANCELLED">CANCELLED</option>
                    </select>
                  </td>
                </tr>
              ))}

              {items.length === 0 && (
                <tr>
                  <td colSpan={6} className="px-4 py-8 text-center text-slate-500">
                    Chưa có yêu cầu nào
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}