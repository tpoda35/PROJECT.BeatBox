import type { TrackSliderProps } from "./types/TrackSliderProps.ts";
import styles from './TrackSlider.module.css';

import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/navigation';

const TrackSlider = ({ children }: TrackSliderProps) => {
    const slides = Array.isArray(children) ? children : [children];

    return (
        <div className={styles.wrapper}>
            <Swiper
                modules={[Navigation]}
                navigation
                spaceBetween={10}
                breakpoints={{
                    0: { slidesPerView: 4, slidesPerGroup: 4 },
                    680: { slidesPerView: 5, slidesPerGroup: 5 },
                    939:  { slidesPerView: 4, slidesPerGroup: 4 },
                    1100:  { slidesPerView: 5, slidesPerGroup: 5 },
                    1250: { slidesPerView: 6, slidesPerGroup: 6 },
                }}
            >
                {slides.map((child, i) => (
                    <SwiperSlide key={i}>{child}</SwiperSlide>
                ))}
            </Swiper>
        </div>
    );
};

export default TrackSlider;