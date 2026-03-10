import { useEffect, useState } from "react"
import { toast } from "sonner"
import { buildingService } from "@/services/admin/buildingService"
import type { StaffAssignmentItem } from "@/types/admin/staffs"
import {
  Dialog,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog"
import { Button } from "@/components/ui/button"
import { Checkbox } from "@/components/ui/checkbox"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"

type AssignBuildingDialogProps = {
  open: boolean
  onOpenChange: (open: boolean) => void
  buildingId: string | number | null
}

export default function AssignBuildingDialog({
  open,
  onOpenChange,
  buildingId,
}: AssignBuildingDialogProps) {
  const [staffs, setStaffs] = useState<StaffAssignmentItem[]>([])
  const [selectedStaffIds, setSelectedStaffIds] = useState<number[]>([])
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    const fetchStaffs = async () => {
      try {
        if (!open || !buildingId) return

        setLoading(true)

        const res = await buildingService.loadStaffs(buildingId)
        const staffList: StaffAssignmentItem[] = res.data || []

        setStaffs(staffList)

        const checkedIds = staffList
          .filter((item) => item.checked === true || item.checked === "checked")
          .map((item) => item.staffId)

        setSelectedStaffIds(checkedIds)
      } catch (error) {
        console.error(error)
        toast.error("Không tải được danh sách nhân viên")
      } finally {
        setLoading(false)
      }
    }

    fetchStaffs()
  }, [open, buildingId])

  const handleSelectOne = (id: number, checked: boolean) => {
    if (checked) {
      setSelectedStaffIds((prev) =>
        prev.includes(id) ? prev : [...prev, id]
      )
    } else {
      setSelectedStaffIds((prev) => prev.filter((item) => item !== id))
    }
  }

  const handleClose = () => {
    onOpenChange(false)
    setStaffs([])
    setSelectedStaffIds([])
  }

  const handleAssign = async () => {
    try {
      if (!buildingId) {
        toast.error("Không tìm thấy toà nhà cần giao")
        return
      }

      if (selectedStaffIds.length === 0) {
        toast.error("Vui lòng chọn ít nhất một nhân viên")
        return
      }

      await buildingService.assignBuilding({
        buildingId: Number(buildingId),
        staffs: selectedStaffIds,
      })

      toast.success("Giao toà nhà thành công!")
      handleClose()
    } catch (error) {
      console.error(error)
      toast.error("Giao toà nhà thất bại!")
    }
  }

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="max-w-3xl p-0">
        <DialogHeader className="px-6 py-4 border-b border-border">
          <DialogTitle>Danh sách nhân viên</DialogTitle>
        </DialogHeader>

        <div className="px-6 py-5">
          <div className="overflow-hidden border rounded-md border-border">
            <Table>
              <TableHeader>
                <TableRow className="bg-muted/40 border-border">
                  <TableHead className="w-[120px] text-center">Chọn</TableHead>
                  <TableHead>Tên nhân viên</TableHead>
                </TableRow>
              </TableHeader>

              <TableBody>
                {loading ? (
                  <TableRow>
                    <TableCell colSpan={2} className="py-6 text-center text-muted-foreground">
                      Đang tải dữ liệu...
                    </TableCell>
                  </TableRow>
                ) : staffs.length === 0 ? (
                  <TableRow>
                    <TableCell colSpan={2} className="py-6 text-center text-muted-foreground">
                      Không có nhân viên
                    </TableCell>
                  </TableRow>
                ) : (
                  staffs.map((staff) => (
                    <TableRow key={staff.staffId} className="border-border">
                      <TableCell className="text-center">
                        <div className="flex justify-center">
                          <Checkbox
                            checked={selectedStaffIds.includes(staff.staffId)}
                            onCheckedChange={(checked) =>
                              handleSelectOne(staff.staffId, !!checked)
                            }
                            className="cursor-pointer"
                          />
                        </div>
                      </TableCell>

                      <TableCell>{staff.fullName}</TableCell>
                    </TableRow>
                  ))
                )}
              </TableBody>
            </Table>
          </div>
        </div>

        <DialogFooter className="px-6 py-4 border-t bg-muted/20 border-border">
          <Button onClick={handleAssign} disabled={loading} className="cursor-pointer">
            Giao toà nhà
          </Button>
          <Button variant="outline" onClick={handleClose} className="cursor-pointer border-border">
            Đóng
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  )
}