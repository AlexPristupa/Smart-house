import { useCallback } from 'react';
import { Form } from 'antd';
import { ServiceForm } from 'building/components';
import { IService, IServiceForm, IServiceType } from 'building/interfaces';
import { useRouter } from 'building/utils';
import { useAddService } from 'building/hooks';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { errorHandler, IListData, parseTime } from 'services';
import { useQueryClient } from 'react-query';
import { useTranslation } from 'react-i18next';

export const AddService: React.FC = () => {
  const queryClient = useQueryClient();
  const [form] = Form.useForm<IServiceForm>();
  const { t } = useTranslation();

  const {
    params: { buildingId },
    goToBuildingPage,
  } = useRouter();

  const { mutate, isLoading } = useAddService(parseInt(buildingId!), {
    onMutate: async service => {
      await queryClient.cancelQueries([BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId]);

      const previousService = queryClient.getQueryData<IListData<IService>>([BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId]);
      const previousServiceType = queryClient.getQueryData<IListData<IServiceType>>(BUILDING_QUERY_KEYS.SERVICE);

      if (previousServiceType) {
        const serviceType = previousService?.content.find(item => item.id === service.serviceWorkType);
        if (serviceType) {
          const newService = Object.assign({}, service) as unknown as IService;
          newService.type = serviceType;

          queryClient.setQueryData<IListData<IService>>([BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId], old => {
            const buildingOld = old as unknown as IListData<IService>;
            console.log('buildingOld', buildingOld);

            if (buildingOld.content) {
              buildingOld.content = [...buildingOld.content, newService];
            } else {
              buildingOld.content = [newService];
            }
            return buildingOld;
          });
        }
      }

      return { previousService };
    },
    onSuccess: () => {
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId]);
      form.resetFields();
      goToBuildingPage();
    },
    onError: (err, context) => {
      const previousData = context as unknown as IListData<IServiceForm>;
      queryClient.setQueryData([BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId], previousData);
      errorHandler(err, form);
    },
    onSettled: () => {
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId]);
    },
  });

  const onCancel = useCallback(() => {
    goToBuildingPage();
  }, [goToBuildingPage]);

  const formatTime = (data: string): string => {
    // HH:mm
    if (data) return data.length > 5 ? `${parseTime(data, '{h}:{i}')}:00` : `${data}:00`;

    return '00:00:00';
  };

  const onFinish = useCallback(
    (values: IServiceForm) => {
      const tempStartTime = new Date(`${parseTime(values.startTime, '{y}-{m}-{d}')} ${formatTime(values.timeStart)}`).toISOString();
      const tempFinishtTime = new Date(`${parseTime(values.finishTime, '{y}-{m}-{d}')} ${formatTime(values.timeFinish)}`).toISOString();

      const addValues = {
        ...values,
        startTime: tempStartTime,
        finishTime: tempFinishtTime,
      };

      mutate(addValues);
    },
    [mutate],
  );

  return (
    <ServiceForm form={form} isLoading={isLoading} onCancel={onCancel} onFinish={onFinish} title={t('service.form.title.addService')} />
  );
};
