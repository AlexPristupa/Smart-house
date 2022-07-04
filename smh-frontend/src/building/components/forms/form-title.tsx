import styled from 'styled-components';

const FormTitleH1 = styled.h1`
  font-size: 32px;
`;

interface IProps {
  title?: string;
}
export const FormTitle: React.FC<IProps> = ({ title }) => <FormTitleH1>{title}</FormTitleH1>;
