import { roleService } from "@/services/admin/roleService";
import type { RoleItem } from "@/types/admin/roles";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { toast } from "sonner";

export default function RoleList() {
  const [items, setItems] = useState<RoleItem[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchRoles = async () => {
    try {
      setLoading(true);
      const res = await roleService.getRoles();
      setItems(res.data || []);
    } catch (error) {
      console.error(error);
      toast.error("Không tải được danh sách nhóm quyền");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchRoles();
  }, []);

  const handleDelete = async (id: number) => {
    const confirmDelete = window.confirm("Bạn có chắc muốn xoá nhóm quyền này?");
    if (!confirmDelete) return;

    try {
      await roleService.deleteRole(id);
      toast.success("Xoá nhóm quyền thành công");
      fetchRoles();
    } catch (error) {
      console.error(error);
      toast.error("Xoá nhóm quyền thất bại");
    }
  };

  return (
    <div className="p-6 bg-white border rounded-xl">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-xl font-semibold">Danh sách nhóm quyền</h1>

        <Link
          to="/admin/roles/create"
          className="px-4 py-2 text-white bg-violet-600 rounded-xl"
        >
          Thêm nhóm quyền
        </Link>
      </div>

      <table className="w-full border-collapse">
        <thead>
          <tr className="border-b">
            <th className="p-3 text-left">Code</th>
            <th className="p-3 text-left">Tên</th>
            <th className="p-3 text-left">Mô tả</th>
            <th className="p-3 text-left">Quyền thao tác</th>
            <th className="p-3 text-left">Hành động</th>
          </tr>
        </thead>

        <tbody>
          {loading ? (
            <tr>
              <td colSpan={5} className="p-3 text-center">
                Đang tải...
              </td>
            </tr>
          ) : items.length === 0 ? (
            <tr>
              <td colSpan={5} className="p-3 text-center">
                Không có dữ liệu
              </td>
            </tr>
          ) : (
            items.map((item) => (
              <tr key={item.id} className="border-b">
                <td className="p-3">{item.code}</td>
                <td className="p-3">{item.name}</td>
                <td className="p-3">{item.description || "-"}</td>
                <td className="p-3">
                  <div className="flex flex-wrap gap-2">
                    {item.permissions?.map((permission) => (
                      <span
                        key={permission.id}
                        className="px-2 py-1 text-xs rounded bg-slate-100"
                      >
                        {permission.code}
                      </span>
                    ))}
                  </div>
                </td>
                <td className="p-3">
                  <div className="flex gap-2">
                    <Link
                      to={`/admin/roles/${item.id}/edit`}
                      className="px-3 py-1 text-white rounded-lg bg-amber-500"
                    >
                      Sửa
                    </Link>

                    <button
                      onClick={() => handleDelete(item.id)}
                      className="px-3 py-1 text-white bg-red-500 rounded-lg"
                    >
                      Xoá
                    </button>
                  </div>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}