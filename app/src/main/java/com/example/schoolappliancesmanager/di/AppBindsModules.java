package com.example.schoolappliancesmanager.di;

import com.example.schoolappliancesmanager.model.repository.ApplianceRepository;
import com.example.schoolappliancesmanager.model.repository.Impl.DefaultApplianceRepositoryImpl;
import com.example.schoolappliancesmanager.model.repository.Impl.DefaultDetailUsedRepositoryImpl;
import com.example.schoolappliancesmanager.model.repository.Impl.DefaultRoomRepositoryImpl;
import com.example.schoolappliancesmanager.model.repository.DetailUsedRepository;
import com.example.schoolappliancesmanager.model.repository.RoomRepository;
import com.example.schoolappliancesmanager.model.usecase.AddApplianceUseCase;
import com.example.schoolappliancesmanager.model.usecase.AddRoomUseCase;
import com.example.schoolappliancesmanager.model.usecase.ApplianceStatisticalChartUseCase;
import com.example.schoolappliancesmanager.model.usecase.ApplianceStatisticalUseCase;
import com.example.schoolappliancesmanager.model.usecase.AppliancesUseCase;
import com.example.schoolappliancesmanager.model.usecase.BorrowUseCase;
import com.example.schoolappliancesmanager.model.usecase.Impl.DefaultAddApplianceUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.Impl.DefaultAddRoomUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.Impl.DefaultApplianceApplianceStatisticalChartUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.Impl.DefaultApplianceApplianceStatisticalUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.Impl.DefaultAppliancesUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.Impl.DefaultBorrowUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.Impl.DefaultDetailUsedUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.Impl.DefaultRoomsUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.DetailUsedUseCase;
import com.example.schoolappliancesmanager.model.usecase.RoomsUseCase;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppBindsModules {
    @Binds
    abstract ApplianceRepository getApplianceRepository(DefaultApplianceRepositoryImpl repository);
    @Binds
    abstract DetailUsedRepository getDetailUsedRepository(DefaultDetailUsedRepositoryImpl repository);
    @Binds
    abstract RoomRepository getRoomRepository(DefaultRoomRepositoryImpl repository);
    @Binds
    abstract AddRoomUseCase getAddRoomUseCase(DefaultAddRoomUseCaseImpl useCase);
    @Binds
    abstract AddApplianceUseCase getAddApplianceUseCase(DefaultAddApplianceUseCaseImpl useCase);
    @Binds
    abstract BorrowUseCase getBorrowUseCase(DefaultBorrowUseCaseImpl useCase);
    @Binds
    abstract AppliancesUseCase getAppliancesUseCase(DefaultAppliancesUseCaseImpl useCase);
    @Binds
    abstract DetailUsedUseCase getDetailUsedUseCase(DefaultDetailUsedUseCaseImpl useCase);
    @Binds
    abstract RoomsUseCase getRoomsUseCase(DefaultRoomsUseCaseImpl useCase);
    @Binds
    abstract ApplianceStatisticalUseCase getStatisticalUseCase(DefaultApplianceApplianceStatisticalUseCaseImpl useCase);
    @Binds
    abstract ApplianceStatisticalChartUseCase getApplianceStatisticalChartUseCase(DefaultApplianceApplianceStatisticalChartUseCaseImpl useCase);
}
