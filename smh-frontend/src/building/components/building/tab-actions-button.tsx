import { Button as BaseButton } from 'antd';
import { BUILDNG_CONTENTS } from 'building/constants';
import { useRouter } from 'building/utils';
import { useTranslation } from 'react-i18next';
import styled from 'styled-components';
import { UploadButtonDocs } from './upload-button-docs';

const Button = styled(BaseButton)`
  width: 100%;
`;

export const TabActionsButton: React.FC = () => {
  const {
    params: { contents },
    goToInsertPage,
  } = useRouter();

  const { t } = useTranslation();

  const contentType = contents as BUILDNG_CONTENTS;

  const getButton = (label: string) => {
    const btn = (
      <Button type="primary" onClick={goToInsertPage}>
        {label}
      </Button>
    );

    return btn;
  };

  switch (contentType) {
    case BUILDNG_CONTENTS.SERVICE: {
      return getButton(t('building.page.button.addService'));
    }
    case BUILDNG_CONTENTS.HARDWARE:
      return getButton(t('building.page.button.addHardware'));
    case BUILDNG_CONTENTS.DOCUMENTATION:
      return <UploadButtonDocs />;
    default:
      return getButton(t('building.page.button.addService'));
  }
};
