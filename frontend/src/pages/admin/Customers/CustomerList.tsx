import { useEffect } from "react";
import { Link } from "react-router-dom";
import { useCustomerStore } from "@/stores/admin/useCustomerStore";
import { Button } from "@/components/ui/button";
import { customerService } from "@/services/admin/customerService";
import { toast } from "sonner";

export default function CustomerList() {
  const { items, loading, filters, setFilters, search } = useCustomerStore();

  useEffect(() => {
    search();
  }, [search]);

  const handleDelete = async (id: number) => {
    try {
      await customerService.delete(id);
      toast.success("Xóa khách hàng thành công");
      await search();
    } catch (error) {
      console.error(error);
      toast.error("Xóa khách hàng thất bại");
    }
  };

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <h1 className="text-xl font-semibold">Danh sách khách hàng</h1>
        <Link to="/admin/customers/create">
          <Button>Thêm khách hàng</Button>
        </Link>
      </div>

      <div className="grid grid-cols-4 gap-3">
        <input
          value={filters.fullName}
          onChange={(e) => setFilters({ fullName: e.target.value })}
          placeholder="Tên khách hàng"
          className="p-3 border rounded-xl"
        />

        <input
          value={filters.phone}
          onChange={(e) => setFilters({ phone: e.target.value })}
          placeholder="Số điện thoại"
          className="p-3 border rounded-xl"
        />

        <input
          value={filters.email}
          onChange={(e) => setFilters({ email: e.target.value })}
          placeholder="Email"
          className="p-3 border rounded-xl"
        />

        <select
          value={filters.status}
          onChange={(e) => setFilters({ status: e.target.value })}
          className="p-3 border rounded-xl"
        >
          <option value="">-- Chọn trạng thái --</option>
          <option value="MOI">Mới</option>
          <option value="DANG_CHAM_SOC">Đang chăm sóc</option>
          <option value="DA_CHOT">Đã chốt</option>
          <option value="NGUNG_THEO_DOI">Ngừng theo dõi</option>
        </select>
      </div>

      <div className="flex gap-2">
        <Button onClick={search}>Tìm kiếm</Button>
        <Button
          variant="outline"
          onClick={() => {
            setFilters({
              fullName: "",
              phone: "",
              email: "",
              staffId: "",
              status: "",
              page: 1,
              limit: 10,
            });
            search();
          }}
        >
          Reset
        </Button>
      </div>

      <div className="overflow-hidden bg-white border rounded-xl">
        <table className="w-full">
          <thead>
            <tr className="border-b bg-slate-50">
              <th className="p-3 text-left">Tên</th>
              <th className="p-3 text-left">Phone</th>
              <th className="p-3 text-left">Email</th>
              <th className="p-3 text-left">Công ty</th>
              <th className="p-3 text-left">Trạng thái</th>
              <th className="p-3 text-center">Hành động</th>
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
                  <td className="p-3">{item.fullName}</td>
                  <td className="p-3">{item.phone}</td>
                  <td className="p-3">{item.email}</td>
                  <td className="p-3">{item.companyName}</td>
                  <td className="p-3">{item.status}</td>
                  <td className="p-3">
                    <div className="flex justify-center gap-2">
                      <Link to={`/admin/customers/${item.id}/edit`}>
                        <Button size="sm">Sửa</Button>
                      </Link>

                      <Button
                        size="sm"
                        variant="destructive"
                        onClick={() => handleDelete(item.id)}
                      >
                        Xóa
                      </Button>
                    </div>
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