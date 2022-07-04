interface IGetFioProps {
  lastName: string;
  firstName: string;
  patronymic?: string;
}

export const getFio = ({ lastName, firstName, ...rest }: IGetFioProps) => {
  const patronymic = rest.patronymic ? ` ${rest.patronymic}` : '';

  return `${lastName} ${firstName}${patronymic}`;
};
