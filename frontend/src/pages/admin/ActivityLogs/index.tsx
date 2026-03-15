import { useEffect } from "react";
import { useActivityLogStore } from "@/stores/admin/useActivityLogStore";
import { Button } from "@/components/ui/button";

export default function ActivityLogListPage() {
  const { items, loading, filters, setFilters, resetFilters, search } =
    useActivityLogStore();

  useEffect(() => {
    search();
  }, [search]);

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">Nhật ký hoạt động</h1>

      <div className="grid grid-cols-1 gap-3 md:grid-cols-3">
        <input
          value={filters.actorEmail}
          onChange={(e) => setFilters({ actorEmail: e.target.value })}
          placeholder="Email người thao tác"
          className="w-full p-3 border rounded-xl"
        />

        <select
          value={filters.action}
          onChange={(e) => setFilters({ action: e.target.value })}
          className="w-full p-3 border rounded-xl"
        >
          <option value="">-- Chọn action --</option>
          <option value="CREATE">CREATE</option>
          <option value="UPDATE">UPDATE</option>
          <option value="DELETE">DELETE</option>
          <option value="RESET_PASSWORD">RESET_PASSWORD</option>
          <option value="FORGOT_PASSWORD">FORGOT_PASSWORD</option>
          <option value="ASSIGN">ASSIGN</option>
        </select>

        <select
          value={filters.module}
          onChange={(e) => setFilters({ module: e.target.value })}
          className="w-full p-3 border rounded-xl"
        >
          <option value="">-- Chọn module --</option>
          <option value="USER">USER</option>
          <option value="ROLE">ROLE</option>
          <option value="BUILDING">BUILDING</option>
          <option value="CUSTOMER">CUSTOMER</option>
          <option value="TRANSACTION">TRANSACTION</option>
          <option value="PROFILE">PROFILE</option>
          <option value="AUTH">AUTH</option>
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
              <th className="p-3 text-left">Người thao tác</th>
              <th className="p-3 text-left">Action</th>
              <th className="p-3 text-left">Module</th>
              <th className="p-3 text-left">Nội dung</th>
              <th className="p-3 text-left">Object ID</th>
            </tr>
          </thead>

          <tbody>
            {loading ? (
              <tr>
                <td colSpan={6} className="p-4 text-center">
                  Đang tải...
                </td>
              </tr>
            ) : items.length === 0 ? (
              <tr>
                <td colSpan={6} className="p-4 text-center">
                  Không có dữ liệu
                </td>
              </tr>
            ) : (
              items.map((item) => (
                <tr key={item.id} className="border-b">
                  <td className="p-3">{item.createdDate}</td>
                  <td className="p-3">
                    <div>{item.actorName}</div>
                    <div className="text-sm text-slate-500">{item.actorEmail}</div>
                  </td>
                  <td className="p-3">{item.action}</td>
                  <td className="p-3">{item.module}</td>
                  <td className="p-3">{item.description}</td>
                  <td className="p-3">{item.objectId ?? ""}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}