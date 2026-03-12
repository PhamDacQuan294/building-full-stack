import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { userService } from "@/services/admin/userService";
import type { UserItem } from "@/types/admin/users";

export default function UserList() {
  const [items, setItems] = useState<UserItem[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        setLoading(true);
        const res = await userService.getUsers();
        setItems(res.data || []);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []);

  return (
    <div className="p-6 bg-white border rounded-xl border-border">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-xl font-semibold">Danh sách người dùng</h1>

        <Link
          to="/admin/users/create"
          className="px-4 py-2 text-white rounded bg-violet-600"
        >
          Thêm người dùng
        </Link>
      </div>

      <table className="w-full border-collapse">
        <thead>
          <tr className="border-b">
            <th className="p-2 text-left">Họ tên</th>
            <th className="p-2 text-left">Email</th>
            <th className="p-2 text-left">Số điện thoại</th>
            <th className="p-2 text-left">Trạng thái</th>
            <th className="p-2 text-left">Nhóm quyền</th>
          </tr>
        </thead>
        <tbody>
          {loading ? (
            <tr>
              <td className="p-2" colSpan={5}>
                Đang tải...
              </td>
            </tr>
          ) : items.length === 0 ? (
            <tr>
              <td className="p-2" colSpan={5}>
                Không có dữ liệu
              </td>
            </tr>
          ) : (
            items.map((item) => (
              <tr key={item.id} className="border-b">
                <td className="p-2">{item.fullName}</td>
                <td className="p-2">{item.email}</td>
                <td className="p-2">{item.phone}</td>
                <td className="p-2">{item.status}</td>
                <td className="p-2">{item.roles.join(", ")}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}