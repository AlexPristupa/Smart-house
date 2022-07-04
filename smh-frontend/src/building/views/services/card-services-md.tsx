import { Card, Col, Row } from 'antd';
import styled from 'styled-components';
import React from 'react';
import { parseTime } from 'services';
import { IService } from 'building/interfaces';
import { useTranslation } from 'react-i18next';

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
  display: flex;
  align-items: center;
  word-break: break-word;
`;

const CardTitleH1 = styled.h1`
  font-size: 32px;
`;

interface IProps {
  data: IService;
}

export const CardServiceMd: React.FC<IProps> = ({ data }) => {
  const { t } = useTranslation();

  if (data) {
    return (
      <Card bordered={false}>
        <Title>
          <CardTitle>
            <CardTitleH1>{data.name}</CardTitleH1>
          </CardTitle>
        </Title>
        <Row>
          <Col span={20}>
            <CardRow>
              <Col lg={{ span: 8 }}>
                <CardLabel>{t('service.card.type')}</CardLabel>
              </Col>
              <Col lg={{ span: 8, offset: 1 }}>
                <CardValue>{data.type.name}</CardValue>
              </Col>
            </CardRow>
            <CardRow>
              <Col lg={{ span: 8 }}>
                <CardLabel>{t('service.card.startTime')}</CardLabel>
              </Col>
              <Col lg={{ span: 8, offset: 1 }}>
                <CardValue>{parseTime(data.startTime, '{d}.{m}.{y} {h}:{i}')}</CardValue>
              </Col>
            </CardRow>
            <CardRow>
              <Col lg={{ span: 8 }}>
                <CardLabel>{t('service.card.finishTime')}</CardLabel>
              </Col>
              <Col lg={{ span: 8, offset: 1 }}>
                <CardValue>{parseTime(data.finishTime, '{d}.{m}.{y} {h}:{i}')}</CardValue>
              </Col>
            </CardRow>
            <CardRow>
              <Col lg={{ span: 8 }}>
                <CardLabel>{t('service.card.statusTitle')}</CardLabel>
              </Col>
              <Col lg={{ span: 4, offset: 1 }}>
                <CardValue>
                  {data.resolution === t('button.resolutionBtn.resolved')
                    ? t('service.form.title.resolved')
                    : t('service.form.title.unresolved')}
                </CardValue>
              </Col>
            </CardRow>
            <CardRow>
              <Col lg={{ span: 8 }}>
                <CardLabel>{t('service.card.description')}</CardLabel>
              </Col>
              <Col lg={{ span: 15, offset: 1 }}>
                <CardValue style={{ wordBreak: 'break-word' }}>{data.description}</CardValue>
              </Col>
            </CardRow>
          </Col>
        </Row>
      </Card>
    );
  }

  return null;
};
