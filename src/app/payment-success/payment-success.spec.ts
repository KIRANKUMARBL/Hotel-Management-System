import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PaymentSuccess } from './payment-success';
import { ActivatedRoute } from '@angular/router';

describe('PaymentSuccess', () => {

  let component: PaymentSuccess;
  let fixture: ComponentFixture<PaymentSuccess>;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [PaymentSuccess],

      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              queryParamMap: {
                get: () => '1'
              }
            }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(PaymentSuccess);

    component = fixture.componentInstance;

    // IMPORTANT:
    // DO NOT WRITE fixture.detectChanges()

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});