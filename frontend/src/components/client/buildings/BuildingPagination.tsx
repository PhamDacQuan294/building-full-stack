type Props = {
  page: number;
  totalPages: number;
  onPageChange: (page: number) => void;
};

export default function BuildingPagination({ page, totalPages, onPageChange }: Props) {
  if (totalPages <= 1) return null;

  return (
    <div className="flex items-center justify-center gap-2">
      <button
        disabled={page === 1}
        onClick={() => onPageChange(page - 1)}
        className="px-4 py-2 border rounded-xl disabled:opacity-50"
      >
        Trước
      </button>

      {Array.from({ length: totalPages }, (_, index) => index + 1).map((item) => (
        <button
          key={item}
          onClick={() => onPageChange(item)}
          className={`rounded-xl px-4 py-2 ${
            page === item
              ? "bg-violet-600 text-white"
              : "border border-slate-300 text-slate-700"
          }`}
        >
          {item}
        </button>
      ))}

      <button
        disabled={page === totalPages}
        onClick={() => onPageChange(page + 1)}
        className="px-4 py-2 border rounded-xl disabled:opacity-50"
      >
        Sau
      </button>
    </div>
  );
}