package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.ActivityTypeRepository;
import gradation.implementation.datatier.repositories.AddressRepository;
import gradation.implementation.datatier.repositories.CommentRepository;
import gradation.implementation.datatier.repositories.LevelRepository;
import gradation.implementation.presentationtier.form.ActivityForm;
import gradation.implementation.presentationtier.form.ActivityTypeForm;
import gradation.implementation.presentationtier.form.CommentForm;
import gradation.implementation.presentationtier.form.LevelForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ActivitySettingServiceImplementationTest {
    @Mock
    private LevelRepository levelRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ActivityTypeRepository activityTypeRepository;
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private ActivitySettingServiceImplementation activitySettingServiceImplementation;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findSpecificLevel() {
        Long id = 1L;
        Level level = new Level();
        level.setId(id);
        level.setName("level");

        given(levelRepository.findSpecific(id)).willReturn(level);
        Level result = activitySettingServiceImplementation.findSpecificLevel(id);

        assertEquals(level.getName(), result.getName());
        verify(levelRepository, times(1)).findSpecific(id);
        verifyNoMoreInteractions(levelRepository);
    }

    @Test
    public void findBeginner() {
        Level level = new Level();
        level.setName("level");

        given(levelRepository.findSpecific(1L)).willReturn(level);
        Level result = activitySettingServiceImplementation.findBeginner();

        assertEquals(level.getName(), result.getName());
        verify(levelRepository, times(1)).findSpecific(1L);
        verifyNoMoreInteractions(levelRepository);
    }

    @Test
    public void initiateCommentForm() {
        Activity activity = new Activity();
        SportsMan sportsMan = new SportsMan();
        sportsMan.setFirstName("test");
        CommentForm commentForm = activitySettingServiceImplementation.initiateCommentForm(activity, sportsMan);
        assertEquals("test", commentForm.getAuthor().getFirstName());
    }

    @Test
    public void createComment() {
        Comment comment = new Comment();
        given(commentRepository.save(comment)).willReturn(comment);
        assertNotNull(commentRepository.save(comment));
    }

    @Test
    public void getAllActivityTypes() {
        ActivityType activityType = new ActivityType(), activityType1 = new ActivityType();
        Iterable<ActivityType> types = Arrays.asList(activityType,activityType1);

        given(activityTypeRepository.findAll()).willReturn(types);
        Iterable<ActivityType> result = activitySettingServiceImplementation.getAllActivityTypes();

        assertEquals(types, result);
        verify(activityTypeRepository, times(1)).findAll();
        verifyNoMoreInteractions(activityTypeRepository);
    }

    @Test
    public void findSpecificActivityType() {
        Long id = 1L;
        ActivityType activityType = new ActivityType();
        activityType.setId(id);
        activityType.setName("level");

        given(activityTypeRepository.findSpecific(id)).willReturn(activityType);
        ActivityType result = activitySettingServiceImplementation.findSpecificActivityType(id);

        assertEquals(activityType.getName(), result.getName());
        verify(activityTypeRepository, times(1)).findSpecific(id);
        verifyNoMoreInteractions(activityTypeRepository);
    }

    @Test
    public void createType() {
        ActivityTypeForm activityTypeForm = new ActivityTypeForm();
        ActivityType activityType = new ActivityType();
/*
        given(activityTypeRepository.save(activityType)).willReturn(activityType);
*/
        activitySettingServiceImplementation.createType(activityTypeForm);
/*        verify(activityTypeRepository, times(1)).save(any(ActivityType.class));
        verifyNoMoreInteractions(activityTypeRepository);*/
    }

    @Test
    public void updateType() {
        ActivityTypeForm activityTypeForm = new ActivityTypeForm();
        activityTypeForm.setName("test");
        ActivityType activityType = new ActivityType();
        activitySettingServiceImplementation.updateType(activityType, activityTypeForm);
        assertEquals(activityType.getName(), activityTypeForm.getName());
    }

    @Test
    public void getAllLevels() {
        Level level = new Level(), level1 = new Level();
        Iterable<Level> levels = Arrays.asList(level,level1);

        given(levelRepository.findAll()).willReturn(levels);
        Iterable<Level> result = activitySettingServiceImplementation.getAllLevels();

        assertEquals(levels, result);
        verify(levelRepository, times(1)).findAll();
        verifyNoMoreInteractions(levelRepository);
    }

    @Test
    public void updateLevel() {
        LevelForm levelForm = new LevelForm();
        levelForm.setName("test");
        Level level = new Level();
        activitySettingServiceImplementation.updateLevel(levelForm,level);
        assertEquals(levelForm.getName(), level.getName());
    }

    @Test
    public void findCommentsForActivity() {
        Comment comment = new Comment();
        Activity activity = new Activity();
        comment.setActivity(activity);
        List<Comment> comments = Arrays.asList(comment);
        given(commentRepository.findForEvent(activity)).willReturn(comments);
        Iterable<Comment> result = activitySettingServiceImplementation.findCommentsForActivity(activity);
        assertEquals(comments, result);
    }

    @Test
    public void createAddress() {
        ActivityForm activityForm = new ActivityForm();
        Address address = new Address();
/*
        given(addressRepository.save(address)).willReturn(address);
*/
        activitySettingServiceImplementation.createAddress(activityForm);
        /*verify(addressRepository, times(1)).save(any(Address.class));
        verifyNoMoreInteractions(addressRepository);*/
    }

    @Test
    public void updateAddress() {
        ActivityForm activityForm = new ActivityForm();
        activityForm.setCountry("test");
        Address address = new Address();
        activitySettingServiceImplementation.updateAddress(address,activityForm);
        assertEquals(activityForm.getCountry(), address.getCountry());
    }

    @Test
    public void findSpecificAddress() {
        Activity activity = new Activity();
        Address address = new Address();
        address.setId(1L);
        address.setCountry("Belgique");
        activity.setAddress(address);

        given(addressRepository.findSpecific(activity.getAddress().getId())).willReturn(address);
        Address result = activitySettingServiceImplementation.findSpecificAddress(activity);

        assertEquals(address.getCountry(), result.getCountry());
        verify(addressRepository, times(1)).findSpecific(1L);
        verifyNoMoreInteractions(addressRepository);
    }
}