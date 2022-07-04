import { Avatar } from 'antd';

interface IProps {
  str: string;
}

export const IconForList: React.FC<IProps> = ({ str }) => {
  if (str) {
    str = str.charAt(0).toUpperCase();
  }

  return <Avatar style={{ color: '#fff', backgroundColor: '#107f8c' }}>{str}</Avatar>;
};
