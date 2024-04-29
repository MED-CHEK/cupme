export interface PromoCodeDTO {
  id?: number;
  code: string;
  discount: number;
  createdDate?: string;
  activated: boolean;
}
