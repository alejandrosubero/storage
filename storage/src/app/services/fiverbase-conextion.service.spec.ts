import { TestBed } from '@angular/core/testing';

import { FiverbaseConextionService } from './fiverbase-conextion.service';

describe('FiverbaseConextionService', () => {
  let service: FiverbaseConextionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FiverbaseConextionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
