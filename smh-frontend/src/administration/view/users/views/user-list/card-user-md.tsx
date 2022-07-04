import { Avatar, Card, Col, Row } from 'antd';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { IUser } from 'administration/view/users/interfaces';
import { getFileSrc, getFio, IFileContent } from 'services';
import { UserOutlined } from '@ant-design/icons';
import React from 'react';
import { CardHardwareImage } from 'ui';

const CardLabel = styled.span`
  font-size: 18px;
  color: #c0c1c2;
`;

const CardValue = styled.span`
  font-size: 18px;
`;

const CardRow = styled(Row)`
  margin-top: 15px;
`;

const Title = styled.div`
  display: flex;
  margin-bottom: 15px;
`;

const CardTitle = styled.div`
  margin-left: 15px;
  display: flex;
  align-items: center;
  word-break: break-word;
`;

const CardTitleH1 = styled.h1`
  padding-left: 20px;
  font-size: 32px;
`;

const MainCard = styled.div`
  margin: 15px;
`;
interface IProps {
  data: IUser;
}

export const CardUsersMd: React.FC<IProps> = ({ data }) => {
  const { t } = useTranslation();

  return (
    <MainCard>
      <Card bordered={false}>
        <Title>
          {data.photo ? (
            <CardHardwareImage
              image={data && data.photo && !Array.isArray(data.photo) ? getFileSrc(data.photo as IFileContent, true) : undefined}
            />
          ) : (
            <div>
              <Avatar size={200} style={{ borderRadius: '40px' }} icon={!data.photo && <UserOutlined size={200} />} />
            </div>
          )}
          <CardTitle>
            <CardTitleH1>{getFio(data)}</CardTitleH1>
          </CardTitle>
        </Title>
        <Row>
          <Col span={16}>
            <CardRow>
              <Col lg={{ span: 8 }}>
                <CardLabel>{t('users.card.email')}</CardLabel>
              </Col>
              <Col lg={{ span: 4, offset: 1 }}>
                <CardValue>{data.email}</CardValue>
              </Col>
            </CardRow>
            <CardRow>
              <Col lg={{ span: 8 }}>
                <CardLabel>{t('users.card.role')}</CardLabel>
              </Col>
              <Col lg={{ span: 4, offset: 1 }}>
                <CardValue>{t<any>(`users.roles.${data.role}`)}</CardValue>
              </Col>
            </CardRow>
          </Col>
        </Row>
      </Card>
    </MainCard>
  );
};
