package com.example.schoolappliancesmanager.di;

import com.example.schoolappliancesmanager.model.repository.ApplianceRepository;
import com.example.schoolappliancesmanager.model.repository.DefaultApplianceRepositoryImpl;
import com.example.schoolappliancesmanager.model.repository.DefaultDetailUsedRepositoryImpl;
import com.example.schoolappliancesmanager.model.repository.DefaultRoomRepositoryImpl;
import com.example.schoolappliancesmanager.model.repository.DetailUsedRepository;
import com.example.schoolappliancesmanager.model.repository.RoomRepository;
import com.example.schoolappliancesmanager.model.usecase.AddApplianceUseCase;
import com.example.schoolappliancesmanager.model.usecase.AddRoomUseCase;
import com.example.schoolappliancesmanager.model.usecase.AppliancesUseCase;
import com.example.schoolappliancesmanager.model.usecase.BorrowUseCase;
import com.example.schoolappliancesmanager.model.usecase.DefaultAddApplianceUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.DefaultAddRoomUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.DefaultAppliancesUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.DefaultBorrowUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.DefaultDetailUsedUseCaseImpl;
import com.example.schoolappliancesmanager.model.usecase.DefaultRoomsUseCaseImpl;
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
}
