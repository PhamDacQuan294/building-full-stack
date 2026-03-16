import { useEffect } from "react";
import { useBuildingStore } from "@/stores/client/useBuildingStore";
import { useHomeStore } from "@/stores/client/useHomeStore";

export default function BuildingFilter() {
  const { districts, loadHomePage } = useHomeStore();
  const { filters, setFilters, fetchBuildings, resetFilters } = useBuildingStore();

  useEffect(() => {
    loadHomePage();
  }, [loadHomePage]);

  const handleSearch = async () => {
    setFilters({ page: 1 });
    await fetchBuildings();
  };

  return (
    <div className="p-6 bg-white border shadow-sm rounded-3xl border-slate-200">
      <div className="grid grid-cols-1 gap-4 md:grid-cols-2 xl:grid-cols-4">
        <input
          value={filters.keyword}
          onChange={(e) => setFilters({ keyword: e.target.value })}
          placeholder="Tìm theo tên, đường, phường..."
          className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-200 focus:border-violet-500"
        />

        <select
          value={filters.district}
          onChange={(e) => setFilters({ district: e.target.value })}
          className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-200 focus:border-violet-500"
        >
          <option value="">Chọn quận</option>
          {districts.map((district) => (
            <option key={district} value={district}>
              {district}
            </option>
          ))}
        </select>

        <input
          value={filters.rentPriceFrom}
          onChange={(e) => setFilters({ rentPriceFrom: e.target.value })}
          placeholder="Giá từ"
          className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-200 focus:border-violet-500"
        />

        <input
          value={filters.rentPriceTo}
          onChange={(e) => setFilters({ rentPriceTo: e.target.value })}
          placeholder="Giá đến"
          className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-200 focus:border-violet-500"
        />

        <input
          value={filters.areaFrom}
          onChange={(e) => setFilters({ areaFrom: e.target.value })}
          placeholder="Diện tích từ"
          className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-200 focus:border-violet-500"
        />

        <input
          value={filters.areaTo}
          onChange={(e) => setFilters({ areaTo: e.target.value })}
          placeholder="Diện tích đến"
          className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-200 focus:border-violet-500"
        />

        <select
          value={filters.rentType}
          onChange={(e) => setFilters({ rentType: e.target.value })}
          className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-200 focus:border-violet-500"
        >
          <option value="">Loại thuê</option>
          <option value="TANG_TRET">Tầng trệt</option>
          <option value="NGUYEN_CAN">Nguyên căn</option>
          <option value="NOI_THAT">Nội thất</option>
        </select>

        <select
          value={filters.sortBy}
          onChange={(e) => setFilters({ sortBy: e.target.value })}
          className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-200 focus:border-violet-500"
        >
          <option value="newest">Mới nhất</option>
          <option value="priceAsc">Giá tăng dần</option>
          <option value="priceDesc">Giá giảm dần</option>
          <option value="areaAsc">Diện tích tăng dần</option>
          <option value="areaDesc">Diện tích giảm dần</option>
        </select>
      </div>

      <div className="flex gap-3 mt-5">
        <button
          onClick={handleSearch}
          className="px-5 py-3 font-medium text-white rounded-2xl bg-violet-600 hover:bg-violet-700"
        >
          Tìm kiếm
        </button>

        <button
          onClick={resetFilters}
          className="px-5 py-3 font-medium border rounded-2xl border-slate-300 text-slate-700 hover:bg-slate-50"
        >
          Đặt lại
        </button>
      </div>
    </div>
  );
}