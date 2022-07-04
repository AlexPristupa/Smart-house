import { Button, Upload } from 'antd';
import { UploadChangeParam } from 'antd/lib/upload';
import { UploadFile } from 'antd/lib/upload/interface';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { useAddDocumentationFiles } from 'building/hooks';
import { useRouter } from 'building/utils';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useQueryClient } from 'react-query';

export const UploadButtonDocs: React.FC = () => {
  const [val, setVal] = useState<Array<File>>([]);

  const {
    params: { buildingId },
  } = useRouter();

  const { t } = useTranslation();

  const queryClient = useQueryClient();

  const { mutate } = useAddDocumentationFiles({
    onSuccess: () => {
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.DOCUMENTATION_LIST, buildingId]);
    },
    onError: err => {
      console.log(err);
    },
  });

  const handleChange = ({ fileList }: UploadChangeParam<UploadFile>) => {
    const files = fileList.map(file => file.originFileObj as File);

    let difference = files.filter(x => !val.includes(x));

    setVal(files);

    mutate(difference);
  };

  return (
    <Upload
      maxCount={50}
      beforeUpload={() => {
        // предотавращение отправки запроса
        return false;
      }}
      multiple
      showUploadList={false}
      onChange={handleChange}
    >
      <Button type="primary" style={{ width: '100%' }}>
        {t('building.page.button.addDocument')}
      </Button>
    </Upload>
  );
};
