import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { useBuildingDetailStore } from "@/stores/client/useBuildingDetailStore";
import ContactRequestForm from "@/components/client/contact/ContactRequestForm";

function formatVND(value?: number) {
  if (!value || value <= 0) return "Liên hệ";
  return new Intl.NumberFormat("vi-VN").format(value) + " VND";
}

export default function ClientBuildingDetailPage() {
  const { id } = useParams();
  const { item, loading, fetchDetail } = useBuildingDetailStore();

  useEffect(() => {
    if (id) {
      fetchDetail(Number(id));
    }
  }, [id, fetchDetail]);

  if (loading) {
    return <div className="py-10 text-center">Đang tải dữ liệu...</div>;
  }

  if (!item) {
    return <div className="py-10 text-center">Không tìm thấy bất động sản</div>;
  }

  return (
    <div className="min-h-screen bg-slate-50">
      <div className="px-6 py-10 mx-auto max-w-7xl lg:px-8">
        <div className="grid grid-cols-1 gap-8 lg:grid-cols-2">
          <div className="overflow-hidden bg-white shadow-sm rounded-3xl">
            <img
              src={
                item.imageUrl ||
                "https://via.placeholder.com/900x600?text=Building"
              }
              alt={item.name}
              className="h-full max-h-[500px] w-full object-cover"
            />
          </div>

          <div className="p-6 space-y-6 bg-white shadow-sm rounded-3xl">
            <div>
              <h1 className="text-3xl font-bold text-slate-900">{item.name}</h1>
              <p className="mt-2 text-slate-500">{item.address}</p>
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div className="p-4 rounded-2xl bg-slate-50">
                <div className="text-sm text-slate-500">Giá thuê</div>
                <div className="mt-1 font-semibold text-violet-600">
                  {formatVND(item.rentPrice)}
                </div>
              </div>

              <div className="p-4 rounded-2xl bg-slate-50">
                <div className="text-sm text-slate-500">Diện tích</div>
                <div className="mt-1 font-semibold text-slate-900">
                  {item.floorArea || 0} m²
                </div>
              </div>

              <div className="p-4 rounded-2xl bg-slate-50">
                <div className="text-sm text-slate-500">Loại thuê</div>
                <div className="mt-1 font-semibold text-slate-900">
                  {item.rentType || "Đang cập nhật"}
                </div>
              </div>

              <div className="p-4 rounded-2xl bg-slate-50">
                <div className="text-sm text-slate-500">Trạng thái</div>
                <div className="mt-1 font-semibold text-slate-900">
                  {item.status || "ACTIVE"}
                </div>
              </div>
            </div>

            <div>
              <h2 className="text-xl font-bold text-slate-900">Mô tả</h2>
              <p className="mt-2 leading-7 text-slate-600">
                {item.description ||
                  "Chưa có mô tả chi tiết cho bất động sản này."}
              </p>
            </div>

            <div className="p-4 border rounded-2xl border-slate-200">
              <h2 className="text-lg font-bold text-slate-900">
                Thông tin tư vấn
              </h2>
              <div className="mt-3 space-y-2 text-sm text-slate-600">
                <div>Người quản lý: {item.managerName || "Đang cập nhật"}</div>
                <div>Số điện thoại: {item.managerPhone || "Đang cập nhật"}</div>
              </div>
            </div>

            <div className="flex flex-wrap gap-3">
              <button className="px-5 py-3 font-medium text-white cursor-pointer rounded-2xl bg-violet-600 hover:bg-violet-700">
                Liên hệ ngay
              </button>

              <button className="px-5 py-3 font-medium border cursor-pointer rounded-2xl border-slate-300 text-slate-700 hover:bg-slate-50">
                Đăng ký xem nhà
              </button>

              <button className="px-5 py-3 font-medium border cursor-pointer rounded-2xl border-slate-300 text-slate-700 hover:bg-slate-50">
                Gửi yêu cầu tư vấn
              </button>
            </div>
          </div>
        </div>

        <div className="mt-8">
          <ContactRequestForm buildingId={item.id} buildingName={item.name} />
        </div>
      </div>
    </div>
  );
}
