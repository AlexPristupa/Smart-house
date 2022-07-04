import { Card, Col, Row } from 'antd';
import { getFileSrc, IFileContent, parseTime } from 'services';
import styled from 'styled-components';
import { CardHardwareImage } from 'ui';
import { IHardware } from 'building/interfaces';
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
  data: IHardware;
}

export const CardHardwareMd: React.FC<IProps> = ({ data }) => {
  const { t } = useTranslation();

  return (
    <MainCard>
      <Card bordered={false}>
        <Title>
          <CardHardwareImage
            image={data && data.photo && !Array.isArray(data.photo) ? getFileSrc(data.photo as IFileContent, true) : undefined}
          />
          <CardTitle>
            <CardTitleH1>{data ? data.name : ''}</CardTitleH1>
          </CardTitle>
        </Title>
        <Row>
          <Col span={14}>
            <CardRow>
              <Col lg={{ span: 8 }}>
                <CardLabel>{t('hardware.card.model')}</CardLabel>
              </Col>
              <Col lg={{ span: 4, offset: 1 }}>
                <CardValue>{data?.model}</CardValue>
              </Col>
            </CardRow>
            <CardRow>
              <Col lg={{ span: 8 }}>
                <CardLabel>{t('hardware.card.serialNumber')}</CardLabel>
              </Col>
              <Col lg={{ span: 4, offset: 1 }}>
                <CardValue>{data?.serialNumber}</CardValue>
              </Col>
            </CardRow>
            <CardRow>
              <Col lg={{ span: 8 }}>
                <CardLabel>{t('hardware.card.installedAt')}</CardLabel>
              </Col>
              <Col lg={{ span: 4, offset: 1 }}>
                <CardValue>{parseTime(data?.installedAt, '{d}.{m}.{y}')}</CardValue>
              </Col>
            </CardRow>
            <CardRow>
              <Col lg={{ span: 8 }}>
                <CardLabel>{t('hardware.card.expiresAt')}</CardLabel>
              </Col>
              <Col lg={{ span: 4, offset: 1 }}>
                <CardValue>{parseTime(data?.expiresAt, '{d}.{m}.{y}')}</CardValue>
              </Col>
            </CardRow>
            <CardRow>
              <Col lg={{ span: 8 }}>
                <CardLabel>{t('hardware.card.installer')}</CardLabel>
              </Col>
              <Col lg={{ span: 4, offset: 1 }}>
                <CardValue style={{ wordBreak: 'break-word' }}>{data?.installer}</CardValue>
              </Col>
            </CardRow>
          </Col>
        </Row>
      </Card>
    </MainCard>
  );
};
