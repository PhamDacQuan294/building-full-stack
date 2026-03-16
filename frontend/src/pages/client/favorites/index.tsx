import { useEffect } from "react";
import { useFavoriteStore } from "@/stores/client/useFavoriteStore";
import BuildingListCard from "@/components/client/buildings/BuildingListCard";

export default function ClientFavoritePage() {
  const { items, loading, fetchFavorites } = useFavoriteStore();

  useEffect(() => {
    fetchFavorites();
  }, [fetchFavorites]);

  return (
    <div className="min-h-screen bg-slate-50">
      <div className="px-6 py-10 mx-auto space-y-6 max-w-7xl lg:px-8">
        <div>
          <h1 className="text-3xl font-bold text-slate-900">Bất động sản yêu thích</h1>
          <p className="mt-2 text-slate-500">
            Danh sách các bất động sản bạn đã lưu
          </p>
        </div>

        {loading ? (
          <div className="p-8 bg-white shadow-sm rounded-3xl text-slate-500">
            Đang tải dữ liệu...
          </div>
        ) : items.length === 0 ? (
          <div className="p-8 bg-white shadow-sm rounded-3xl text-slate-500">
            Bạn chưa lưu bất động sản nào.
          </div>
        ) : (
          <div className="grid grid-cols-1 gap-6 md:grid-cols-2 xl:grid-cols-4">
            {items.map((item) => (
              <BuildingListCard
                key={item.favoriteId}
                item={{
                  id: item.buildingId,
                  name: item.name,
                  address: item.address,
                  district: item.district,
                  ward: item.ward,
                  street: item.street,
                  imageUrl: item.imageUrl,
                  rentPrice: item.rentPrice,
                  floorArea: item.floorArea,
                  rentType: item.rentType,
                  status: item.status,
                }}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
}