import { useEffect } from "react";
import { Link } from "react-router-dom";
import { useRoleStore } from "@/stores/admin/useRoleStore";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";

export default function RoleList() {
  const { items, loading, fetchRoles } = useRoleStore();

  useEffect(() => {
    fetchRoles();
  }, [fetchRoles]);

  return (
    <Card className="rounded-xl border-border">
      <CardHeader className="flex flex-row items-center justify-between">
        <CardTitle className="text-base">Danh sách nhóm quyền</CardTitle>

        <Button asChild>
          <Link to="/admin/roles/create">Thêm mới nhóm quyền</Link>
        </Button>
      </CardHeader>

      <CardContent>
        <div className="overflow-x-auto border rounded-lg border-border">
          <table className="w-full border-collapse">
            <thead className="bg-muted/40">
              <tr>
                <th className="px-4 py-3 text-left border-b border-border">STT</th>
                <th className="px-4 py-3 text-left border-b border-border">Tên nhóm quyền</th>
                <th className="px-4 py-3 text-left border-b border-border">Mã nhóm quyền</th>
                <th className="px-4 py-3 text-left border-b border-border">Hành động</th>
              </tr>
            </thead>
            <tbody>
              {loading ? (
                <tr>
                  <td colSpan={4} className="px-4 py-6 text-center text-muted-foreground">
                    Đang tải dữ liệu...
                  </td>
                </tr>
              ) : items.length === 0 ? (
                <tr>
                  <td colSpan={4} className="px-4 py-6 text-center text-muted-foreground">
                    Không có dữ liệu
                  </td>
                </tr>
              ) : (
                items.map((item, index) => (
                  <tr key={item.id}>
                    <td className="px-4 py-3 border-b border-border">{index + 1}</td>
                    <td className="px-4 py-3 border-b border-border">{item.name}</td>
                    <td className="px-4 py-3 border-b border-border">{item.code}</td>
                    <td className="px-4 py-3 border-b border-border">
                      <Button size="sm" asChild>
                        <Link to={`/admin/roles/${item.id}/edit`}>Sửa</Link>
                      </Button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </CardContent>
    </Card>
  );
}