import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BookingDetail } from './booking-detail';
import { ActivatedRoute } from '@angular/router';

describe('BookingDetail', () => {

  let component: BookingDetail;
  let fixture: ComponentFixture<BookingDetail>;

  beforeEach(async () => {

    await TestBed.configureTestingModule({

      imports: [BookingDetail],

      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: () => '1'
              }
            }
          }
        }
      ]

    }).compileComponents();

    fixture = TestBed.createComponent(BookingDetail);

    component = fixture.componentInstance;

    // IMPORTANT:
    // avoid ngOnInit API calls
    // DO NOT use fixture.detectChanges()

  });

  it('should create', () => {

    expect(component).toBeTruthy();

  });

});