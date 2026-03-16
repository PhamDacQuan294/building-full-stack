import { useEffect } from "react";
import { Link } from "react-router-dom";
import { useUserStore } from "@/stores/admin/useUserStore";
import { userService } from "@/services/admin/userService";
import { Button } from "@/components/ui/button";
import { toast } from "sonner";
import { getAuthoritiesFromToken } from "@/utils/auth";

export default function UserList() {
  const { items, loading, filters, setFilters, resetFilters, search, totalItems } = useUserStore();

  const authorities = getAuthoritiesFromToken();
    
  const canView = authorities.includes("USER_VIEW");
  const canCreate = authorities.includes("USER_CREATE");
  const canEdit = authorities.includes("USER_EDIT");

  useEffect(() => {
    if (canView) {
      search();
    }
  }, [search, canView]);

  const handleChangeStatus = async (id: number, currentStatus: string) => {
    try {
      const nextStatus = currentStatus === "ACTIVE" ? "INACTIVE" : "ACTIVE";
      await userService.changeStatus(id, nextStatus);
      toast.success("Đổi trạng thái thành công");
      await search();
    } catch (error) {
      console.error(error);
      toast.error("Đổi trạng thái thất bại");
    }
  };

  const handleResetPassword = async (id: number) => {
    const newPassword = window.prompt("Nhập mật khẩu mới");
    if (!newPassword) return;

    try {
      await userService.resetPassword(id, newPassword);
      toast.success("Reset mật khẩu thành công");
    } catch (error) {
      console.error(error);
      toast.error("Reset mật khẩu thất bại");
    }
  };

  if (!canView) {
    return <div className="p-6 bg-white border rounded-xl">Bạn không có quyền xem người dùng</div>;
  }

  const totalPages = Math.ceil(totalItems / filters.limit);

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold">Danh sách người dùng</h1>

        {canCreate && (
          <Link to="/admin/users/create">
            <Button>Thêm người dùng</Button>
          </Link>
        )}
      </div>

      <div className="grid grid-cols-1 gap-3 md:grid-cols-4">
        <input
          value={filters.fullName}
          onChange={(e) => setFilters({ fullName: e.target.value })}
          placeholder="Tìm theo tên"
          className="w-full p-3 border rounded-xl"
        />

        <input
          value={filters.email}
          onChange={(e) => setFilters({ email: e.target.value })}
          placeholder="Tìm theo email"
          className="w-full p-3 border rounded-xl"
        />

        <input
          value={filters.phone}
          onChange={(e) => setFilters({ phone: e.target.value })}
          placeholder="Tìm theo số điện thoại"
          className="w-full p-3 border rounded-xl"
        />

        <select
          value={filters.status}
          onChange={(e) => setFilters({ status: e.target.value })}
          className="w-full p-3 border rounded-xl"
        >
          <option value="">-- Chọn trạng thái --</option>
          <option value="ACTIVE">ACTIVE</option>
          <option value="INACTIVE">INACTIVE</option>
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
              <th className="p-3 text-left">Họ tên</th>
              <th className="p-3 text-left">Email</th>
              <th className="p-3 text-left">Phone</th>
              <th className="p-3 text-left">Username</th>
              <th className="p-3 text-left">Vai trò</th>
              <th className="p-3 text-left">Trạng thái</th>
              <th className="p-3 text-left">Hành động</th>
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
                  <td className="p-3">{item.fullName}</td>
                  <td className="p-3">{item.email}</td>
                  <td className="p-3">{item.phone}</td>
                  <td className="p-3">{item.username}</td>
                  <td className="p-3">{item.roles?.join(", ")}</td>
                  <td className="p-3">{item.status}</td>
                  <td className="p-3">
                    <div className="flex gap-2">
                      {canEdit && (
                        <>
                          <Link to={`/admin/users/${item.id}/edit`}>
                            <Button size="sm">Sửa</Button>
                          </Link>

                          <Button
                            size="sm"
                            variant="outline"
                            onClick={() => handleChangeStatus(item.id, item.status)}
                          >
                            {item.status === "ACTIVE" ? "Khóa" : "Mở khóa"}
                          </Button>

                          <Button
                            size="sm"
                            variant="secondary"
                            onClick={() => handleResetPassword(item.id)}
                          >
                            Reset mật khẩu
                          </Button>
                        </>
                      )}
                    </div>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      <div className="flex items-center justify-end gap-2">
        <Button
          variant="outline"
          disabled={filters.page <= 1}
          onClick={() => {
            setFilters({ page: filters.page - 1 });
            setTimeout(() => search(), 0);
          }}
        >
          Prev
        </Button>

        <span>
          Trang {filters.page} / {totalPages || 1}
        </span>

        <Button
          variant="outline"
          disabled={filters.page >= totalPages}
          onClick={() => {
            setFilters({ page: filters.page + 1 });
            setTimeout(() => search(), 0);
          }}
        >
          Next
        </Button>
      </div>
    </div>
  );
}