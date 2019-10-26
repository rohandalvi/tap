package activity;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import component.CreateProfileComponent;
import implementations.ProfileDao;
import implementations.UserProfile;

public class ActivityModule extends AbstractModule {

    private static final Regions REGION = Regions.US_WEST_2;

    @Provides
    @Singleton
    private ProfileDao getProfileDao(AmazonDynamoDBClient amazonDynamoDBClient) {
        return new ProfileDao(amazonDynamoDBClient);
    }

    @Provides
    @Singleton
    private UserProfile getUserProfile(ProfileDao profileDao) {
        return new UserProfile(profileDao);
    }

    @Provides
    @Singleton
    private CreateProfileComponent createProfileComponent(UserProfile userProfile) {
        return new CreateProfileComponent(userProfile);
    }

    @Provides
    @Singleton
    private AmazonDynamoDBClient getAmazonDynamoDBClient(AWSCredentials awsCredentials) {

        AmazonDynamoDBClient amazonDynamoDBClient = new AmazonDynamoDBClient(awsCredentials);
        amazonDynamoDBClient.setRegion(Region.getRegion(REGION));
        return amazonDynamoDBClient;
    }

    @Provides
    @Singleton
    private AWSCredentials getAWSCredentials() {
        return new EnvironmentVariableCredentialsProvider().getCredentials();
    }
    @Override
    protected void configure() {
        //bind(IProfile.class).to(UserProfile.class);
    }
}
