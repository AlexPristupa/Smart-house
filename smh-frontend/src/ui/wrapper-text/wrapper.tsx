import { Typography } from 'antd';

import { ParagraphProps } from 'antd/lib/typography/Paragraph';
import { EllipsisConfig } from 'antd/lib/typography/Base';
interface IWrapperTextProps extends Omit<ParagraphProps, 'ellipsis'> {
  ellipsis?: EllipsisConfig;
}

export const WrapperText: React.FC<IWrapperTextProps> = ({ children, ellipsis, ...restProps }) => {
  const { rows, tooltip, ...restEllipsis } = ellipsis!;
  return (
    <Typography.Paragraph
      {...restProps}
      ellipsis={{ ...restEllipsis, rows: rows || 1, tooltip: tooltip || children }}
      style={{ margin: '0' }}
    >
      {children}
    </Typography.Paragraph>
  );
};
