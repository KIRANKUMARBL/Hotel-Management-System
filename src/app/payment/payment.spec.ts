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
                get: () => '1'
              },

              queryParamMap: {
                get: () => '2000'
              }

            }

          }
        }
      ]

    }).compileComponents();

    fixture = TestBed.createComponent(Payment);

    component = fixture.componentInstance;

    // IMPORTANT:
    // DO NOT RUN fixture.detectChanges()

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});