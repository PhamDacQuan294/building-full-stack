import { Search, MapPin, Wallet, ArrowRightLeft } from "lucide-react";
import { Button } from "@/components/ui/button";
import { useHomeStore } from "@/stores/client/useHomeStore";

export default function HomeSearchBox() {
  const { filters, setFilters, districts, searchBuildings } = useHomeStore();

  return (
    <div className="overflow-hidden rounded-[28px] border border-white/40 bg-white/95 shadow-2xl backdrop-blur">
      <div className="px-6 py-5 border-b border-slate-100">
        <div className="flex flex-col gap-1 md:flex-row md:items-center md:justify-between">
          <div>
            <h3 className="text-xl font-bold text-slate-900">Tìm kiếm nhanh</h3>
            <p className="text-sm text-slate-500">
              Tìm theo tên tòa nhà, quận/huyện và khoảng giá mong muốn
            </p>
          </div>

          <div className="inline-flex items-center px-3 py-1 mt-2 text-xs font-medium rounded-full bg-violet-50 text-violet-700 md:mt-0">
            Bộ lọc cơ bản
          </div>
        </div>
      </div>

      <div className="p-6">
        <div className="grid grid-cols-1 gap-4 md:grid-cols-2 xl:grid-cols-4">
          <div className="space-y-2">
            <label className="text-sm font-medium text-slate-700">Từ khóa</label>
            <div className="flex items-center px-4 py-3 transition border rounded-2xl border-slate-200 bg-slate-50 focus-within:border-violet-500 focus-within:bg-white focus-within:ring-4 focus-within:ring-violet-100">
              <Search className="w-4 h-4 mr-3 text-slate-400" />
              <input
                value={filters.keyword}
                onChange={(e) => setFilters({ keyword: e.target.value })}
                placeholder="Tên tòa nhà, đường, phường..."
                className="w-full text-sm bg-transparent outline-none text-slate-900 placeholder:text-slate-400"
              />
            </div>
          </div>

          <div className="space-y-2">
            <label className="text-sm font-medium text-slate-700">Khu vực</label>
            <div className="flex items-center px-4 py-3 transition border rounded-2xl border-slate-200 bg-slate-50 focus-within:border-violet-500 focus-within:bg-white focus-within:ring-4 focus-within:ring-violet-100">
              <MapPin className="w-4 h-4 mr-3 text-slate-400" />
              <select
                value={filters.district}
                onChange={(e) => setFilters({ district: e.target.value })}
                className="w-full text-sm bg-transparent outline-none text-slate-900"
              >
                <option value="">Chọn quận / huyện</option>
                {districts.map((district) => (
                  <option key={district} value={district}>
                    {district}
                  </option>
                ))}
              </select>
            </div>
          </div>

          <div className="space-y-2">
            <label className="text-sm font-medium text-slate-700">Giá từ</label>
            <div className="flex items-center px-4 py-3 transition border rounded-2xl border-slate-200 bg-slate-50 focus-within:border-violet-500 focus-within:bg-white focus-within:ring-4 focus-within:ring-violet-100">
              <Wallet className="w-4 h-4 mr-3 text-slate-400" />
              <input
                type="number"
                value={filters.rentPriceFrom}
                onChange={(e) => setFilters({ rentPriceFrom: e.target.value })}
                placeholder="Ví dụ: 1000000"
                className="w-full text-sm bg-transparent outline-none text-slate-900 placeholder:text-slate-400"
              />
            </div>
          </div>

          <div className="space-y-2">
            <label className="text-sm font-medium text-slate-700">Giá đến</label>
            <div className="flex items-center px-4 py-3 transition border rounded-2xl border-slate-200 bg-slate-50 focus-within:border-violet-500 focus-within:bg-white focus-within:ring-4 focus-within:ring-violet-100">
              <ArrowRightLeft className="w-4 h-4 mr-3 text-slate-400" />
              <input
                type="number"
                value={filters.rentPriceTo}
                onChange={(e) => setFilters({ rentPriceTo: e.target.value })}
                placeholder="Ví dụ: 5000000"
                className="w-full text-sm bg-transparent outline-none text-slate-900 placeholder:text-slate-400"
              />
            </div>
          </div>
        </div>

        <div className="flex flex-col gap-3 pt-5 mt-5 border-t border-slate-100 md:flex-row md:items-center md:justify-between">
          <p className="text-sm text-slate-500">
            Gợi ý: tìm theo quận trung tâm hoặc nhập khoảng giá để lọc nhanh hơn
          </p>

          <Button
            onClick={searchBuildings}
            className="h-12 px-6 text-sm font-semibold text-white transition shadow-lg rounded-2xl bg-violet-600 shadow-violet-200 hover:bg-violet-700"
          >
            Tìm kiếm ngay
          </Button>
        </div>
      </div>
    </div>
  );
}