import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Payment } from './payment';
import { ActivatedRoute } from '@angular/router';

describe('Payment', () => {

  let component: Payment;
  let fixture: ComponentFixture<Payment>;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [Payment],

      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: (key: string) => {
                  if (key === 'bookingId') return '1';
                  return null;
                }
              },

              queryParamMap: {
                get: (key: string) => {
                  if (key === 'amount') return '2000';
                  if (key === 'orderId') return 'ORD123';
                  return null;
                }
              }
            }
          }
        }
      ]

    }).compileComponents();

    fixture = TestBed.createComponent(Payment);
    component = fixture.componentInstance;

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});