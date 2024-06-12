/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseReq } from '../models/BaseReq';
import type { BuildBuildingReq } from '../models/BuildBuildingReq';
import type { ConfirmReq } from '../models/ConfirmReq';
import type { EditTeacherReq } from '../models/EditTeacherReq';
import type { GetAllLessonsReq } from '../models/GetAllLessonsReq';
import type { GetAllTeachersReq } from '../models/GetAllTeachersReq';
import type { GetLessonReq } from '../models/GetLessonReq';
import type { LoginReq } from '../models/LoginReq';
import type { RegisterReq } from '../models/RegisterReq';
import type { RegisterStudentReq } from '../models/RegisterStudentReq';
import type { ResponseBoolean } from '../models/ResponseBoolean';
import type { ResponseListMyLessonDTO } from '../models/ResponseListMyLessonDTO';
import type { ResponseListRoom } from '../models/ResponseListRoom';
import type { ResponseLong } from '../models/ResponseLong';
import type { ResponsePageLessonDTO } from '../models/ResponsePageLessonDTO';
import type { ResponsePageTeacherDTO } from '../models/ResponsePageTeacherDTO';
import type { ResponsePageUserDTO } from '../models/ResponsePageUserDTO';
import type { ResponseTeacher } from '../models/ResponseTeacher';
import type { ResponseUserDTO } from '../models/ResponseUserDTO';
import type { ResponseVoid } from '../models/ResponseVoid';
import type { SelectLessonReq } from '../models/SelectLessonReq';
import type { SetLessonReq } from '../models/SetLessonReq';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class Service {
    /**
     * 注册请求
     * @param requestBody
     * @returns ResponseVoid OK
     * @throws ApiError
     */
    public static register(
        requestBody: RegisterReq,
    ): CancelablePromise<ResponseVoid> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/user/register',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * 登录请求
     * @param requestBody
     * @returns ResponseUserDTO OK
     * @throws ApiError
     */
    public static login(
        requestBody: LoginReq,
    ): CancelablePromise<ResponseUserDTO> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/user/login',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * 验证登录
     * @param requestBody
     * @returns ResponseBoolean OK
     * @throws ApiError
     */
    public static confirm(
        requestBody: ConfirmReq,
    ): CancelablePromise<ResponseBoolean> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/user/confirm',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * 注册学生身份
     * @param requestBody
     * @returns ResponseLong OK
     * @throws ApiError
     */
    public static registerStudent(
        requestBody: RegisterStudentReq,
    ): CancelablePromise<ResponseLong> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/teacher/student',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * 创建课程
     * @param requestBody
     * @returns ResponseLong OK
     * @throws ApiError
     */
    public static register1(
        requestBody: SetLessonReq,
    ): CancelablePromise<ResponseLong> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/teacher/set-lesson',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * 获得可用教室
     * @param requestBody
     * @returns ResponseListRoom OK
     * @throws ApiError
     */
    public static getAllOkRoom(
        requestBody: BaseReq,
    ): CancelablePromise<ResponseListRoom> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/teacher/get-ok-room',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * 选课
     * @param requestBody
     * @returns ResponseLong OK
     * @throws ApiError
     */
    public static selectLesson(
        requestBody: SelectLessonReq,
    ): CancelablePromise<ResponseLong> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/student/select-lesson',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * 退课
     * @param requestBody
     * @returns ResponseBoolean OK
     * @throws ApiError
     */
    public static goAwayLesson(
        requestBody: SelectLessonReq,
    ): CancelablePromise<ResponseBoolean> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/student/go-away-lesson',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * 获取课程
     * @param requestBody
     * @returns ResponseListMyLessonDTO OK
     * @throws ApiError
     */
    public static getMyLessons(
        requestBody: GetLessonReq,
    ): CancelablePromise<ResponseListMyLessonDTO> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/student/get-my-lessons',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * 所有老师列表
     * @param requestBody
     * @returns ResponsePageTeacherDTO OK
     * @throws ApiError
     */
    public static getAllTeachers(
        requestBody: GetAllTeachersReq,
    ): CancelablePromise<ResponsePageTeacherDTO> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/student/get-all-teachers',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * 所有课程列表
     * @param requestBody
     * @returns ResponsePageLessonDTO OK
     * @throws ApiError
     */
    public static getAllLessons(
        requestBody: GetAllLessonsReq,
    ): CancelablePromise<ResponsePageLessonDTO> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/student/get-all-lessons',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * 获取所有用户
     * @param requestBody
     * @returns ResponsePageUserDTO OK
     * @throws ApiError
     */
    public static getAllUsers(
        requestBody: GetAllTeachersReq,
    ): CancelablePromise<ResponsePageUserDTO> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/admin/get-all-users',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * 设置老师
     * @param requestBody
     * @returns ResponseTeacher OK
     * @throws ApiError
     */
    public static editTeacher(
        requestBody: EditTeacherReq,
    ): CancelablePromise<ResponseTeacher> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/admin/edit-teacher',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * 设置教学楼
     * @param requestBody
     * @returns ResponseBoolean OK
     * @throws ApiError
     */
    public static buildBuilding(
        requestBody: BuildBuildingReq,
    ): CancelablePromise<ResponseBoolean> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/admin/build-building',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
