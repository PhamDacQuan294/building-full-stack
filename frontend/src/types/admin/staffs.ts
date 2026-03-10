export interface StaffItem {
  id: number
  fullName: string
}

export interface StaffAssignmentItem {
  staffId: number
  fullName: string
  checked?: boolean | string
}