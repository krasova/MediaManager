export interface IPicture {
  id?: string;
  name?: string;
  path?: string;
  size?: string;
  duplicate?: string;
}

export const defaultValue: Readonly<IPicture> = {};
