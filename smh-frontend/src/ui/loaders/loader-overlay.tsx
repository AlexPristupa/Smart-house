import { Spin } from 'antd';
import styled from 'styled-components';

export const Container = styled.div`
  position: relative;
  background-color: white;
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
`;

export const LoaderOverlay: React.FC = () => (
  <Container>
    <Spin />
  </Container>
);
