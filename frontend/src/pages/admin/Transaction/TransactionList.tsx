import { useEffect } from "react";
import { Link } from "react-router-dom";
import { useTransactionStore } from "@/stores/admin/useTransactionStore";
import { Button } from "@/components/ui/button";
import { transactionService } from "@/services/admin/transactionService";
import { toast } from "sonner";
import { getAuthoritiesFromToken } from "@/utils/auth";

export default function TransactionList() {
  const { items, loading, filters, setFilters, resetFilters, search } = useTransactionStore();

  const authorities = getAuthoritiesFromToken();
  
  const canView = authorities.includes("TRANSACTION_VIEW");
  const canCreate = authorities.includes("TRANSACTION_CREATE");
  const canEdit = authorities.includes("TRANSACTION_EDIT");
  const canDelete = authorities.includes("TRANSACTION_DELETE");

  useEffect(() => {
    if (canView) {
      search();
    }
  }, [search, canView]);

  const handleDelete = async (id: number) => {
    try {
      await transactionService.delete(id);
      toast.success("Xóa giao dịch thành công");
      await search();
    } catch (error) {
      console.error(error);
      toast.error("Xóa giao dịch thất bại");
    }
  };

  const handleUpdateStatus = async (id: number, status: string) => {
    try {
      await transactionService.updateStatus(id, status);
      toast.success("Cập nhật trạng thái thành công");
      await search();
    } catch (error) {
      console.error(error);
      toast.error("Cập nhật trạng thái thất bại");
    }
  };

  if (!canView) {
    return (
      <div className="p-6 bg-white border rounded-xl">
        <h2 className="text-lg font-semibold text-red-600">Bạn không có quyền xem giao dịch</h2>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <h1 className="text-xl font-semibold">Danh sách giao dịch</h1>

        {canCreate && (
          <Link to="/admin/transactions/create">
            <Button>Thêm giao dịch</Button>
          </Link>
        )}
      </div>

      <div className="grid grid-cols-3 gap-3">
        <input
          value={filters.customerId}
          onChange={(e) => setFilters({ customerId: e.target.value })}
          placeholder="ID khách hàng"
          className="p-3 border rounded-xl"
        />

        <input
          value={filters.staffId}
          onChange={(e) => setFilters({ staffId: e.target.value })}
          placeholder="ID staff"
          className="p-3 border rounded-xl"
        />

        <select
          value={filters.transactionType}
          onChange={(e) => setFilters({ transactionType: e.target.value })}
          className="p-3 border rounded-xl"
        >
          <option value="">-- Chọn loại giao dịch --</option>
          <option value="DAN_DI_XEM">Dẫn đi xem</option>
          <option value="TU_VAN">Tư vấn</option>
          <option value="DAT_COC">Đặt cọc</option>
          <option value="KY_HOP_DONG">Ký hợp đồng</option>
        </select>

        <select
          value={filters.transactionStatus}
          onChange={(e) => setFilters({ transactionStatus: e.target.value })}
          className="p-3 border rounded-xl"
        >
          <option value="">-- Chọn trạng thái --</option>
          <option value="MOI">Mới</option>
          <option value="DANG_XU_LY">Đang xử lý</option>
          <option value="HOAN_THANH">Hoàn thành</option>
          <option value="HUY">Hủy</option>
        </select>

        <input
          type="date"
          value={filters.fromDate}
          onChange={(e) => setFilters({ fromDate: e.target.value })}
          className="p-3 border rounded-xl"
        />

        <input
          type="date"
          value={filters.toDate}
          onChange={(e) => setFilters({ toDate: e.target.value })}
          className="p-3 border rounded-xl"
        />
      </div>

      <div className="flex gap-2">
        <Button onClick={search}>Tìm kiếm</Button>

        <Button
          variant="outline"
          onClick={() => {
            resetFilters();
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
              <th className="p-3 text-left">Khách hàng</th>
              <th className="p-3 text-left">Staff</th>
              <th className="p-3 text-left">Loại GD</th>
              <th className="p-3 text-left">Trạng thái</th>
              <th className="p-3 text-left">Ngày GD</th>
              <th className="p-3 text-left">Nội dung</th>
              <th className="p-3 text-center">Hành động</th>
            </tr>
          </thead>

          <tbody>
            {loading ? (
              <tr>
                <td colSpan={7} className="p-4 text-center">
                  Đang tải...
                </td>
              </tr>
            ) : items.length === 0 ? (
              <tr>
                <td colSpan={7} className="p-4 text-center">
                  Không có dữ liệu
                </td>
              </tr>
            ) : (
              items.map((item) => (
                <tr key={item.id} className="border-b">
                  <td className="p-3">{item.customerName}</td>
                  <td className="p-3">{item.staffName}</td>
                  <td className="p-3">{item.transactionType}</td>
                  <td className="p-3">
                    {canEdit ? (
                      <select
                        value={item.transactionStatus}
                        onChange={(e) => handleUpdateStatus(item.id, e.target.value)}
                        className="p-2 border rounded-lg"
                      >
                        <option value="MOI">Mới</option>
                        <option value="DANG_XU_LY">Đang xử lý</option>
                        <option value="HOAN_THANH">Hoàn thành</option>
                        <option value="HUY">Hủy</option>
                      </select>
                    ) : (
                      item.transactionStatus
                    )}
                  </td>
                  <td className="p-3">{item.transactionDate}</td>
                  <td className="p-3">{item.content}</td>
                  <td className="p-3">
                    <div className="flex justify-center gap-2">
                      {canEdit && (
                        <Link to={`/admin/transactions/${item.id}/edit`}>
                          <Button size="sm">Sửa</Button>
                        </Link>
                      )}

                      {canDelete && (
                        <Button
                          size="sm"
                          variant="destructive"
                          onClick={() => handleDelete(item.id)}
                        >
                          Xóa
                        </Button>
                      )}
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