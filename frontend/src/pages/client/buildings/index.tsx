import { useEffect } from "react";
import { useBuildingStore } from "@/stores/client/useBuildingStore";
import BuildingFilter from "@/components/client/buildings/BuildingFilter";
import BuildingListCard from "@/components/client/buildings/BuildingListCard";
import BuildingPagination from "@/components/client/buildings/BuildingPagination";

export default function ClientBuildingListPage() {
  const {
    items,
    totalItems,
    totalPages,
    loading,
    filters,
    fetchBuildings,
    changePage,
  } = useBuildingStore();

  useEffect(() => {
    fetchBuildings();
  }, [fetchBuildings]);

  return (
    <div className="min-h-screen bg-slate-50">
      <div className="px-6 py-10 mx-auto space-y-8 max-w-7xl lg:px-8">
        <div>
          <h1 className="text-3xl font-bold text-slate-900">
            Danh sách bất động sản
          </h1>
          <p className="mt-2 text-slate-500">
            Tìm kiếm, lọc và xem chi tiết các bất động sản phù hợp
          </p>
        </div>

        <BuildingFilter />

        <div className="flex items-center justify-between">
          <div className="text-sm text-slate-500">
            Tìm thấy {totalItems} bất động sản
          </div>
        </div>

        {loading ? (
          <div className="py-10 text-center text-slate-500">Đang tải dữ liệu...</div>
        ) : (
          <>
            <div className="grid grid-cols-1 gap-6 md:grid-cols-2 xl:grid-cols-4">
              {items.map((item) => (
                <BuildingListCard key={item.id} item={item} />
              ))}
            </div>

            {items.length === 0 && (
              <div className="py-10 text-center bg-white shadow-sm rounded-3xl text-slate-500">
                Không có dữ liệu phù hợp
              </div>
            )}

            <BuildingPagination
              page={filters.page}
              totalPages={totalPages}
              onPageChange={changePage}
            />
          </>
        )}
      </div>
    </div>
  );
}