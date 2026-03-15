import { useEffect } from "react";
import { useEmailLogStore } from "@/stores/admin/useEmailLogStore";
import { Button } from "@/components/ui/button";

export default function EmailLogListPage() {
  const { items, loading, filters, setFilters, resetFilters, search } = useEmailLogStore();

  useEffect(() => {
    search();
  }, [search]);

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">Lịch sử email</h1>

      <div className="grid grid-cols-1 gap-3 md:grid-cols-3">
        <input
          value={filters.toEmail}
          onChange={(e) => setFilters({ toEmail: e.target.value })}
          placeholder="Email nhận"
          className="w-full p-3 border rounded-xl"
        />

        <select
          value={filters.mailType}
          onChange={(e) => setFilters({ mailType: e.target.value })}
          className="w-full p-3 border rounded-xl"
        >
          <option value="">-- Chọn loại mail --</option>
          <option value="NEW_USER">NEW_USER</option>
          <option value="RESET_PASSWORD">RESET_PASSWORD</option>
          <option value="ASSIGNMENT">ASSIGNMENT</option>
        </select>

        <select
          value={filters.module}
          onChange={(e) => setFilters({ module: e.target.value })}
          className="w-full p-3 border rounded-xl"
        >
          <option value="">-- Chọn module --</option>
          <option value="USER">USER</option>
          <option value="AUTH">AUTH</option>
          <option value="CUSTOMER">CUSTOMER</option>
          <option value="BUILDING">BUILDING</option>
          <option value="TRANSACTION">TRANSACTION</option>
        </select>
      </div>

      <div className="flex gap-2">
        <Button onClick={search}>Tìm kiếm</Button>
        <Button
          variant="outline"
          onClick={() => {
            resetFilters();
            setTimeout(() => search(), 0);
          }}
        >
          Reset
        </Button>
      </div>

      <div className="overflow-hidden bg-white border rounded-2xl">
        <table className="w-full">
          <thead>
            <tr className="border-b bg-slate-50">
              <th className="p-3 text-left">Thời gian</th>
              <th className="p-3 text-left">Người gửi</th>
              <th className="p-3 text-left">Người nhận</th>
              <th className="p-3 text-left">Loại mail</th>
              <th className="p-3 text-left">Module</th>
              <th className="p-3 text-left">Trạng thái</th>
              <th className="p-3 text-left">Tiêu đề</th>
            </tr>
          </thead>

          <tbody>
            {loading ? (
              <tr>
                <td colSpan={7} className="p-4 text-center">Đang tải...</td>
              </tr>
            ) : items.length === 0 ? (
              <tr>
                <td colSpan={7} className="p-4 text-center">Không có dữ liệu</td>
              </tr>
            ) : (
              items.map((item) => (
                <tr key={item.id} className="border-b">
                  <td className="p-3">{item.createdDate}</td>
                  <td className="p-3">
                    <div>{item.actorName || ""}</div>
                    <div className="text-sm text-slate-500">{item.actorEmail || ""}</div>
                  </td>
                  <td className="p-3">
                    <div>{item.receiverName || ""}</div>
                    <div className="text-sm text-slate-500">{item.toEmail}</div>
                  </td>
                  <td className="p-3">{item.mailType}</td>
                  <td className="p-3">{item.module}</td>
                  <td className="p-3">
                    {item.sentSuccess ? (
                      <span className="text-green-600">Thành công</span>
                    ) : (
                      <span className="text-red-600">Thất bại</span>
                    )}
                  </td>
                  <td className="p-3">{item.subject}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}