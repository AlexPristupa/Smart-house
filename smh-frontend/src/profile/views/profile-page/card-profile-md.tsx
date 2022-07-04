import { Avatar, Card, Col, Row } from 'antd';
import { getFileSrc, getFio, IFileContent, IProfile, parseTime } from 'services';
import styled from 'styled-components';
import { CardHardwareImage } from 'ui';
import { useTranslation } from 'react-i18next';
import { UserOutlined } from '@ant-design/icons';
import React from 'react';

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
interface ICardHardwareMdProps {
  data: IProfile;
}

export const CardProfileMd: React.FC<ICardHardwareMdProps> = ({ data: { lastName, firstName, patronymic, email, birthDate, photo } }) => {
  const { t } = useTranslation();

  return (
    <MainCard>
      <Card bordered={false}>
        <Title>
          {photo ? (
            <CardHardwareImage image={photo && photo && !Array.isArray(photo) ? getFileSrc(photo as IFileContent, true) : undefined} />
          ) : (
            <Avatar size={200} style={{ borderRadius: '40px' }} icon={!photo && <UserOutlined />} />
          )}
          <CardTitle>
            <CardTitleH1>{getFio({ lastName, firstName, patronymic })}</CardTitleH1>
          </CardTitle>
        </Title>
        <Row>
          <Col span={14}>
            <CardRow>
              <Col lg={{ span: 8 }}>
                <CardLabel>{t('profile.email')}</CardLabel>
              </Col>
              <Col lg={{ span: 4, offset: 1 }}>
                <CardValue>{email}</CardValue>
              </Col>
            </CardRow>
            {birthDate && (
              <CardRow>
                <Col lg={{ span: 8 }}>
                  <CardLabel>{t('profile.birthDate')}</CardLabel>
                </Col>
                <Col lg={{ span: 4, offset: 1 }}>
                  <CardValue>{parseTime(birthDate, '{d}.{m}.{y}')}</CardValue>
                </Col>
              </CardRow>
            )}
          </Col>
        </Row>
      </Card>
    </MainCard>
  );
};
