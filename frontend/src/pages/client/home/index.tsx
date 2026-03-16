import { useEffect } from "react";
import { useHomeStore } from "@/stores/client/useHomeStore";
import HomeSearchBox from "@/components/client/home/HomeSearchBox";
import BuildingCard from "@/components/client/buildings/BuildingCard";

export default function ClientHomePage() {
  const {
    bannerTitle,
    bannerDescription,
    featuredBuildings,
    newestBuildings,
    highlightDistricts,
    searchItems,
    totalItems,
    loadHomePage,
  } = useHomeStore();

  useEffect(() => {
    loadHomePage();
  }, [loadHomePage]);

  return (
    <div className="min-h-screen bg-slate-50">
      {/* HERO */}
      <section className="relative overflow-hidden bg-gradient-to-r from-violet-700 via-purple-700 to-indigo-600">
        <div className="px-6 py-16 mx-auto max-w-7xl lg:px-8 lg:py-20">
          <div className="max-w-4xl mx-auto text-center text-white">
            <div className="inline-flex px-4 py-1 mb-4 text-sm font-medium border rounded-full border-white/20 bg-white/10 backdrop-blur">
              Nền tảng tìm kiếm bất động sản
            </div>

            <h1 className="text-3xl font-bold leading-tight md:text-5xl">
              {bannerTitle}
            </h1>

            <p className="mt-4 text-base text-white/90 md:text-xl">
              {bannerDescription}
            </p>
          </div>

          <div className="max-w-6xl mx-auto mt-10">
            <HomeSearchBox />
          </div>
        </div>
      </section>

      <div className="px-6 py-10 mx-auto max-w-7xl space-y-14 lg:px-8">
        {/* SEARCH RESULTS */}
        {searchItems.length > 0 && (
          <section className="space-y-6">
            <div className="flex flex-col gap-2 md:flex-row md:items-center md:justify-between">
              <div>
                <h2 className="text-2xl font-bold text-slate-900">
                  Kết quả tìm kiếm
                </h2>
                <p className="text-sm text-slate-500">
                  Các bất động sản phù hợp với điều kiện bạn đã chọn
                </p>
              </div>

              <div className="px-4 py-2 text-sm font-medium bg-white rounded-full shadow-sm text-slate-600 ring-1 ring-slate-200">
                {totalItems} bất động sản
              </div>
            </div>

            <div className="grid grid-cols-1 gap-6 md:grid-cols-2 xl:grid-cols-4">
              {searchItems.map((item) => (
                <BuildingCard key={item.id} item={item} />
              ))}
            </div>
          </section>
        )}

        {/* FEATURED */}
        <section className="space-y-6">
          <div>
            <h2 className="text-2xl font-bold text-slate-900">
              Bất động sản nổi bật
            </h2>
            <p className="mt-1 text-sm text-slate-500">
              Những sản phẩm được quan tâm nhiều và có vị trí tốt
            </p>
          </div>

          <div className="grid grid-cols-1 gap-6 md:grid-cols-2 xl:grid-cols-3">
            {featuredBuildings.map((item) => (
              <BuildingCard key={item.id} item={item} />
            ))}
          </div>
        </section>

        {/* DISTRICTS */}
        <section className="space-y-6">
          <div>
            <h2 className="text-2xl font-bold text-slate-900">Khu vực nổi bật</h2>
            <p className="mt-1 text-sm text-slate-500">
              Các quận/huyện có nhiều bất động sản đáng chú ý
            </p>
          </div>

          <div className="grid grid-cols-2 gap-4 md:grid-cols-3 xl:grid-cols-6">
            {highlightDistricts.map((item) => (
              <div
                key={item.code}
                className="p-5 text-center transition duration-300 bg-white border shadow-sm rounded-2xl border-slate-200 hover:-translate-y-1 hover:shadow-md"
              >
                <div className="text-base font-semibold text-slate-900">
                  {item.name}
                </div>
                <div className="mt-2 text-sm text-slate-500">
                  {item.totalBuildings} bất động sản
                </div>
              </div>
            ))}
          </div>
        </section>

        {/* NEWEST */}
        <section className="space-y-6">
          <div>
            <h2 className="text-2xl font-bold text-slate-900">
              Sản phẩm mới nhất
            </h2>
            <p className="mt-1 text-sm text-slate-500">
              Các bất động sản vừa được cập nhật gần đây
            </p>
          </div>

          <div className="grid grid-cols-1 gap-6 md:grid-cols-2 xl:grid-cols-4">
            {newestBuildings.map((item) => (
              <BuildingCard key={item.id} item={item} />
            ))}
          </div>
        </section>
      </div>
    </div>
  );
}