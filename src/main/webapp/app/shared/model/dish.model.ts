export interface IDish {
  id?: number;
  title?: string;
  description?: string;
  imageContentType?: string | null;
  image?: string | null;
  restaurant?: string;
}

export class Dish implements IDish {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public imageContentType?: string | null,
    public image?: string | null,
    public restaurant?: string,
  ) {}
}
