import { Button, ButtonProps } from 'antd';
import useBreakpoint from 'antd/lib/grid/hooks/useBreakpoint';
import styled from 'styled-components';
import { useIsRole } from 'services';

const ButtonContainer = styled.div`
  position: sticky;
  background-color: rgba(239, 242, 244, 1);
  bottom: 0;
  padding: 20px;
  width: 100%;
`;

export const AddButtonBottom: React.FC<ButtonProps> = ({ children, ...props }) => {
  const { md } = useBreakpoint();
  const { isNotReader } = useIsRole();

  if (!md) {
    return isNotReader() ? (
      <ButtonContainer>
        <Button {...props} type="primary" style={{ width: '100%' }}>
          {children}
        </Button>
      </ButtonContainer>
    ) : (
      <></>
    );
  }

  return null;
};
