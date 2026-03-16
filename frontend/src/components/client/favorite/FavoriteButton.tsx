import { useEffect } from "react";
import { Heart } from "lucide-react";
import { useFavoriteStore } from "@/stores/client/useFavoriteStore";

type Props = {
  buildingId: number;
};

export default function FavoriteButton({ buildingId }: Props) {
  const { favoriteMap, checkFavorite, toggleFavorite } = useFavoriteStore();

  useEffect(() => {
    checkFavorite(buildingId);
  }, [buildingId, checkFavorite]);

  const isFavorite = favoriteMap[buildingId];

  return (
    <button
      type="button"
      onClick={() => toggleFavorite(buildingId)}
      className={`inline-flex items-center gap-2 rounded-2xl px-4 py-2 text-sm font-medium transition ${
        isFavorite
          ? "bg-red-50 text-red-600 border border-red-200"
          : "bg-white text-slate-700 border border-slate-300 hover:bg-slate-50"
      }`}
    >
      <Heart className={`h-4 w-4 ${isFavorite ? "fill-red-500 text-red-500" : ""}`} />
      {isFavorite ? "Đã lưu" : "Lưu tin"}
    </button>
  );
}