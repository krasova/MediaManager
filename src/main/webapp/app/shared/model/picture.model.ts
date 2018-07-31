export interface IPicture {
  id?: string;
  name?: string;
  path?: string;
  size?: string;
  md5?: string;
}

export const defaultValue: Readonly<IPicture> = {};
