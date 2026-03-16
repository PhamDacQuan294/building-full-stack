import type { ClientBuildingCard } from "@/types/client/home";
import { Link } from "react-router-dom";

type Props = {
  item: ClientBuildingCard;
};

function formatVND(value?: number) {
  if (!value || value <= 0) return "Liên hệ";
  return new Intl.NumberFormat("vi-VN").format(value) + " VND";
}

export default function BuildingCard({ item }: Props) {
  return (
    <div className="overflow-hidden transition duration-300 bg-white border shadow-sm rounded-3xl border-slate-200 hover:-translate-y-1 hover:shadow-xl">
      <div className="relative">
        <img
          src={
            item.imageUrl || "https://via.placeholder.com/600x400?text=Building"
          }
          alt={item.name}
          className="object-cover w-full h-56"
        />
        <div className="absolute px-3 py-1 text-xs font-semibold rounded-full shadow left-4 top-4 bg-white/90 text-violet-700">
          {item.status || "Đang mở bán"}
        </div>
      </div>

      <div className="p-5 space-y-3">
        <h3 className="text-xl font-bold line-clamp-1 text-slate-900">
          {item.name}
        </h3>

        <p className="text-sm line-clamp-2 text-slate-500">{item.address}</p>

        <div className="flex items-center justify-between text-sm">
          <span className="text-slate-600">
            Diện tích: {item.floorArea || 0} m²
          </span>
          <span className="font-bold text-violet-600">
            {formatVND(item.rentPrice)}
          </span>
        </div>

        <Link to={`/buildings/${item.id}`}>
          <button className="w-full px-4 py-3 text-sm font-semibold transition border cursor-pointer rounded-2xl border-violet-200 text-violet-700 hover:bg-violet-50">
            Xem chi tiết
          </button>
        </Link>
      </div>
    </div>
  );
}
