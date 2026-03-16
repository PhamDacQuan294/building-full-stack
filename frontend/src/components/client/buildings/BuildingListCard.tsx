import { Link } from "react-router-dom";
import type { ClientBuildingListItem } from "@/types/client/building";

type Props = {
  item: ClientBuildingListItem;
};

function formatVND(value?: number) {
  if (!value || value <= 0) return "Liên hệ";
  return new Intl.NumberFormat("vi-VN").format(value) + " VND";
}

export default function BuildingListCard({ item }: Props) {
  return (
    <div className="overflow-hidden transition bg-white border shadow-sm rounded-3xl border-slate-200 hover:-translate-y-1 hover:shadow-lg">
      <img
        src={item.imageUrl || "https://via.placeholder.com/600x400?text=Building"}
        alt={item.name}
        className="object-cover w-full h-56"
      />

      <div className="p-5 space-y-3">
        <h3 className="text-xl font-bold text-slate-900">{item.name}</h3>

        <p className="text-sm text-slate-500">{item.address}</p>

        <div className="flex items-center justify-between text-sm">
          <span className="text-slate-600">Diện tích: {item.floorArea || 0} m²</span>
          <span className="font-semibold text-violet-600">{formatVND(item.rentPrice)}</span>
        </div>

        <div className="text-sm text-slate-500">
          Loại thuê: {item.rentType || "Đang cập nhật"}
        </div>

        <Link
          to={`/buildings/${item.id}`}
          className="inline-flex px-4 py-2 text-sm font-medium text-white rounded-2xl bg-violet-600 hover:bg-violet-700"
        >
          Xem chi tiết
        </Link>
      </div>
    </div>
  );
}