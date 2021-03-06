export const mockNotifications = {
  content: [
    {
      type: 'hardware_expired',
      read: false,
      facilityId: 113,
      payload: {
        id: 57,
        name: 'Some Device',
        description: 'new demo device',
        photo: null,
        images: [],
        model: 'DVC-1541',
        serialNumber: 'sdf321-5345345-cv',
        installedAt: '2020-11-26T07:06:40Z',
        expiresAt: '2020-11-26T07:06:40Z',
        installer: 'mr. Pupkin',
      },
    },
    {
      type: 'service_work_before_start',
      read: false,
      facilityId: 113,
      payload: {
        id: 81,
        name: 'Test Service 1',
        description: 'new demo service',
        type: {
          id: 2,
          name: 'Платеж',
          description: null,
          icon: null,
          pinned: true,
          linked: false,
        },
        status: 'IN_PROGRESS',
        resolution: 'UNRESOLVED',
        startTime: '2021-10-12T06:50:00Z',
        finishTime: '2021-10-12T06:51:00Z',
        resolutionTime: null,
      },
    },
    {
      type: 'service_work_finished',
      read: false,
      facilityId: 24,
      payload: {
        id: 38,
        name: 'Test Service 1',
        description: 'new demo service',
        type: {
          id: 2,
          name: 'Платеж',
          description: null,
          icon: null,
          pinned: true,
          linked: false,
        },
        status: 'IN_PROGRESS',
        resolution: 'UNRESOLVED',
        startTime: '2021-10-12T06:50:00Z',
        finishTime: '2021-10-12T06:51:00Z',
        resolutionTime: null,
      },
    },
  ],
  empty: true,
  first: true,
  last: true,
  number: 0,
  numberOfElements: 0,
  pageable: {
    page: 0,
    size: 0,
    sort: ['string'],
  },
  size: 0,
  sort: {
    empty: true,
    sorted: true,
    unsorted: true,
  },
  totalElements: 3,
  totalPages: 1,
};
